package hanuman.team

import grails.converters.JSON
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import hanuman.simplegenericrestfulcontroller.generic.StatusCode


class OrderActivityController extends SimpleGenericRestfulController<OrderActivity> {

    def springSecurityService
    def orderActivityService

    OrderActivityController() {
        super(OrderActivity)
    }

    @Override
    def index(PaginationCommand paginationCommand) {
        def odt = OrderActivity.createCriteria().list(paginationCommand.params) {
            eq("orderId", params.long("orderId"))

            if (params.activityType) {
                eq("activityType", params.activityType)
            }
        }

        render JSONFormat.respond(odt, StatusCode.OK) as JSON
    }

    /**
     * Add comment in order.
     * @return
     */
    def addComment () {
        def json = request.JSON
        def user = springSecurityService.getCurrentUser()
        String currentUsername = (user?.firstName ?: "" + user?.lastName ?: "")

        def adt = orderActivityService.addActivity(user?.id as Long, currentUsername, OrderActivityType.COMMENT,"<span style='color:green'>${json.comment} </span>", json.orderId as Long)
        render JSONFormat.respondSingleObject(adt) as JSON
    }
}
