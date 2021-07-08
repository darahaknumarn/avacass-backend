package hanuman.team

import grails.converters.JSON
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import hanuman.simplegenericrestfulcontroller.generic.StatusCode

class DeliveryAddressController extends SimpleGenericRestfulController<DeliveryAddress>{

    DeliveryAddressController() {
        super(DeliveryAddress)
    }

    @Override
    def index(PaginationCommand paginationCommand) {
        def da = DeliveryAddress.createCriteria().list(paginationCommand.params) {
            customer {
                eq("id", params.long("customerId"))
            }
        }

        render JSONFormat.respond(da, StatusCode.OK) as JSON
    }
}
