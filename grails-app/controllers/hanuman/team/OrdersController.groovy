package hanuman.team


import grails.converters.JSON
import grails.gorm.transactions.Transactional
import hanuman.security.SecUser
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.RespondDTO
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import hanuman.simplegenericrestfulcontroller.generic.StatusCode

class OrdersController extends SimpleGenericRestfulController<Orders>{
    def nextCodeService
    def stockTransactionService
    def springSecurityService
    def statusTrackingService
    def orderService
    def applicationConfigurationService
    def notificationContentService
    def orderActivityService
    OrdersController() {
        super(Orders)
    }

    @Override
    def getAction(PaginationCommand paginationCommand){
        Date fromDate = null
        Date toDate = null
        if (params.fromDate)
            fromDate = params.date("fromDate", "yyyy-MM-dd")

        if (params.toDate)
            toDate = params.date("toDate", "yyyy-MM-dd")?.plus(1)?.clearTime()

        def list = Orders.createCriteria().list(paginationCommand.params){
            if(params.orderNo){
                like("orderNo" , "%${params.orderNo}%")
            }
            if(params.customerId){
                eq("customerId" , params.customerId as Long)
            }
            if(params.status){
                like("status","%${params.status}%")
            }
            if(params.isFullFilled){
                eq("isFullFilled" , Boolean.parseBoolean(params.isFullFilled))
            }

            if(params.paymentStatus){
                eq("paymentStatus" , params.paymentStatus)
            }
            if (fromDate){
                ge("dateCreated", fromDate)
            }
            if (toDate) {
                le("dateCreated", toDate)
            }
            eq("isDeleted" , false)
        }
        return list
    }

    @Override
    def beforeSave(Orders orders) {
        orders.orderNo = nextCodeService.getLastCode("Order" , true ,[:])
        return orders
    }

    @Override
    def afterSaved(Orders orders) {
        // reserve stock
        stockTransactionService.reserveStock(orders)
        return orders
    }


    @Override
    def beforeUpdate(Orders order) {

        def user = springSecurityService.getCurrentUser()
        if (order.status.toUpperCase() == "REJECT") {
            order.rejectBy = (user?.firstName ?: "" + user?.lastName ?: "")
            order.rejectDate = new Date()
        }




        return order
    }



    @Override
    @Transactional
    def updateAction() {
        def orders = Orders.get(params.id)
        if (orders == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        def user = springSecurityService.getCurrentUser()
        String oldStatus = orders.status
        def json = request.JSON

//        check assigneee
        if(json.assignTo != orders.assignTo){
            def assigneee = SecUser.get(orders.assignTo)
            orderActivityService.addActivity(user?.id , OrderActivityType.ASSIGN , "assigned to: <b>${assigneee?.firstName} ${assigneee?.lastName} </b>" , orders.id )
        }
//        check on change on status
        if(json.status != orders.status){
            orderActivityService.addActivity(user?.id , OrderActivityType.CHANG_STATUS , "change status from  <b>${json.status}</b> to <b>${orders.status}</b>" , orders.id )
        }

        orders.properties = json
        orders = beforeUpdate(orders)

        if (orders.getClass() == RespondDTO) {
            transactionStatus.setRollbackOnly()
            render orders as JSON
            return
        }
        orders.validate()
        if (orders.hasErrors()) {
            transactionStatus.setRollbackOnly()
            render JSONFormat.respondSingleObject(null, StatusCode.Invalid, StatusCode.Invalid.description, getError(orders)) as JSON
            return
        }
        orders.save(flush: true)
        // update status need push notification to customer's order/checkout.
        orderService.pushNotification(orders)
        // deduct stock of product, when status order accepted.
        if ("ACCEPTED" == orders.status.toUpperCase())
            stockTransactionService.deductStock(orders)

        statusTrackingService.addStatusTracking(orders, oldStatus, getAuthenticatedUser().toString(), getPrincipal().id)
        render JSONFormat.respondSingleObject(orders) as JSON
    }

    @Override
    def delete( ){ // soft delete
      def order =   Orders.get(params.id)
        order.isDeleted = true
        order.save(flush:true )
        respond JSONFormat.respondSingleObject(order)
    }
    def comment(){
        def json =request.JSON
        def user = springSecurityService.getCurrentUser()
        orderActivityService.addActivity(user?.id , OrderActivityType.COMMENT , "<span style='color:green'>${json.comment} </span>" , json.orderId )

    }

}
