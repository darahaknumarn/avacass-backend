package hanuman.team

import grails.converters.JSON
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController

class OrdersController extends SimpleGenericRestfulController<Orders>{
    def nextCodeService

    OrdersController() {
        super(Orders)
    }

    @Override
    def getAction(PaginationCommand paginationCommand){
        def list = Orders.createCriteria().list(paginationCommand.params){
            if(params.orderNo){
                like("orderNo" , "%${params.orderNo}%")
            }
            if(params.customerId){
                eq("customerId" , params.customerId as Integer)
            }
            if(params.isFullfilled){

                eq("isFullfilled" , Boolean.parseBoolean(params.isFullfilled) )
            }

            if(params.paymentStatus){
                eq("paymentStatus" , params.paymentStatus)
            }
            eq("isDeleted" , false)
        }
        return list
    }

    @Override
    def beforeSave(Orders orders ){
        orders.orderNo = nextCodeService.getLastCode("Order" , true ,[:])
        orders.totalQty = orders.orderDetail.qty.sum()
       return orders
    }

    @Override
    def beforeUpdate(Orders orders){
        orders.totalQty = orders.orderDetail.qty.sum()
        return orders
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
