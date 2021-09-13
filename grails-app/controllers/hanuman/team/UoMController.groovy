package hanuman.team

import grails.converters.JSON
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController

class UoMController extends SimpleGenericRestfulController<UoM> {

    UoMController() {
        super(UoM)
    }

    def index(PaginationCommand paginationCommand) {
        def uom = UoM.createCriteria().list(paginationCommand.params) {
            if (params.name) {
                like("name", "%${params.name}%")
            }
        }
        render JSONFormat.respond(uom) as JSON
    }
}
