package hanuman.team

import com.fasterxml.jackson.databind.ObjectMapper
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import hanuman.notification.Notification
import hanuman.security.SecUser

@Transactional
class OrderService {

    def notificationContentService
    /**
     * Notify to customer, when admin update/changed status to "Accepted"
     * @param order
     * @return
     */
    def pushNotification(Orders orders) {
        String title, shortDescription
        JSON json = new JSON(orders)
        HashMap<String, Object> order = new ObjectMapper().readValue(json.toString(), HashMap.class)
        // get customer base on orders/checkout of product.
        def username = Customer.findAllById(orders.customerId).username

        if ("REJECT" == orders.status.toUpperCase()) {
            title = "Your order have been rejected."
            shortDescription = orders.rejectReason
        }else {
            title = "Your order updated status ${orders.status}"
            shortDescription = orders.description
        }
        Notification ntc = new Notification(
                userDevices: username,
                title: title,
                shortDescription: shortDescription,
                nType: "Event Order",
                content: order
        )
        notificationContentService.pushNotification(ntc)
    }
}
