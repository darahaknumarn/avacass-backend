package hanuman.team

import grails.converters.JSON
import grails.gorm.transactions.Transactional
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
                le("dateCreated", params.date("toDate", "yyyy-MM-dd")?.plus(1)?.clearTime())
            }
            eq("isDeleted" , false)
        }
        return list
    }

    @Override
    def beforeUpdate(Orders orders) {
        def user = springSecurityService.getCurrentUser()
        if (orders.status.toUpperCase() == "REJECT") {
            orders.rejectBy = (user.firstName ?: "" + user.lastName ?: "")
        }
        return orders
    }

    @Override
    @Transactional
    def update() {
        def orders = Orders.get(params.id)
        if (orders == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        String oldStatus = orders.status

        orders.properties = request.JSON
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
        if ("ACCEPTED" == orders.status.toUpperCase()) {
            // deduct stock of product, when status order accepted.
            stockTransactionService.deductStock(orders)
        }
        if ("REJECT" == orders.status.toUpperCase()) {
            // push notification.
        }
        orders.save(flush: true)
        statusTrackingService.addStatusTracking(orders, oldStatus, getAuthenticatedUser().toString(), Long.parseLong(getPrincipal().id))
        render JSONFormat.respondSingleObject(orders) as JSON
    }

    @Override
    def show() {
        def object =Orders.get(params.id).properties
        object.orderDetail =  object.orderDetail.findAll {it.isDeleted == false }
        render JSONFormat.respondSingleObject(object) as JSON
    }

    @Override
    def delete( ){ // soft delete
      def order =   Orders.get(params.id)
        order.isDeleted = true
        order.save(flush:true )
        respond JSONFormat.respondSingleObject(order)
    }
}
