package hanuman.team

import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController

class DeliveryAddressController extends SimpleGenericRestfulController<DeliveryAddress>{


    DeliveryAddressController() {
        super(DeliveryAddress)
    }
    @Override
    def index(PaginationCommand paginationCommand){
        def addrs = DeliveryAddress.createCriteria().list (paginationCommand.params){
            if(params.customerId){
                customer {
                    eq("id" ,  params.customerId as Long)
                }

            }
        }

        respond JSONFormat.respond(addrs)
    }
}
