package hanuman.team

import grails.converters.JSON
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import hanuman.simplegenericrestfulcontroller.generic.StatusCode

class VendorController extends SimpleGenericRestfulController<Vendor>{

    VendorController() {
        super(Vendor)
    }

    @Override
    def index(PaginationCommand paginationCommand) {
        def ban = Vendor.createCriteria().list(paginationCommand.params) {
            if(params.name) {
                like("name", "%${params.name}%")
            }
        }

        render JSONFormat.respond(ban, StatusCode.OK, "List banner successfully.") as JSON
    }

}
