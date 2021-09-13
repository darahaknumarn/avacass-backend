package hanuman.team

import grails.converters.JSON
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
class PurchaseController extends SimpleGenericRestfulController<Purchase>{

    PurchaseController() {
        super(Purchase)
    }

    def nextCodeService

    def index(PaginationCommand paginationCommand) {
        Date fromDate = null
        Date toDate = null
        if (params.fromDate)
            fromDate = params.date("fromDate", "yyyy-MM-dd")

        if (params.toDate)
            toDate = params.date("toDate", "yyyy-MM-dd")?.plus(1)?.clearTime()

        def purchase = Purchase.createCriteria().list(paginationCommand.params) {
            if(params.code){
                like("code" , "%${params.code}%")
            }
            if (params.vendorId) {
                eq("vendorId", params.long("vendorId"))
            }
            if (params.categoryId) {
                like("categoryId", params.long("categoryId"))
            }
            if (params.status) {
                eq("status", params.status)
            }
            if (fromDate){
                ge("purchaseDate", fromDate)
            }
            if (toDate) {
                le("purchaseDate", toDate)
            }
        }

        render JSONFormat.respond(purchase) as JSON
    }

    @Override
    def beforeSave(Purchase purchase) {
        purchase.code = nextCodeService.getLastCode("Purchase" , true ,[:])
        return purchase
    }
}
