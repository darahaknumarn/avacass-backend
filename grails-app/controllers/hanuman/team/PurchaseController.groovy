package hanuman.team

import grails.converters.JSON
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
class PurchaseController extends SimpleGenericRestfulController<Purchase>{

    PurchaseController() {
        super(Purchase)
    }

    def index(PaginationCommand paginationCommand) {
        Date purDate = null
        if (params.purchaseDate)
            purDate = params.date("purchaseDate", "yyyy-MM-dd")?.plus(1)?.clearTime()

        def purchase = Purchase.createCriteria().list(paginationCommand.params) {
            if (params.vendorName) {
                like("vendorName", "%${params.vendorName}%")
            }
            if (params.categoryName) {
                like("categoryName", "%${params.categoryName}%")
            }
            if (params.status) {
                eq("status", params.status)
            }
            if (purDate){
                eq("purchaseDate", purDate)
            }
        }

        render JSONFormat.respond(purchase) as JSON
    }
}
