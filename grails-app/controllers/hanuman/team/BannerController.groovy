package hanuman.team

import grails.converters.JSON
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import hanuman.simplegenericrestfulcontroller.generic.StatusCode

class BannerController extends SimpleGenericRestfulController<Banner>{

    BannerController() {
        super(Banner)
    }

    @Override
    def index(PaginationCommand paginationCommand) {
        def ban = Banner.createCriteria().list(paginationCommand.params) {
            eq("isActive", true)
            order ("orderNumber", "desc")

            if(params.name) {
                like("name", "%${params.name}%")
            }
        }

        render JSONFormat.respond(ban, StatusCode.OK, "List banner successfully.") as JSON
    }
}
