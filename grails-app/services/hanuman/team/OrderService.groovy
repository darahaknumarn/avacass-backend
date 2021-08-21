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
    def pushNotification(Orders orders, String status) {
        String title = ""
        String shortDescription = ""
        JSON json = new JSON(orders)
        HashMap<String, Object> order = new ObjectMapper().readValue(json.toString(), HashMap.class)
        // get customer base on orders/checkout of product.
        def username = Customer.findAllById(orders.customerId).username
        title = "Order Update"
        String body = "Your order ${orders.orderNo} at Avacass has been updated ${status}"
        println(body)

        switch (orders.status.toUpperCase()) {
            case "REJECT" : shortDescription = "${body} ${orders.rejectReason}"
                break

            case "DELIVERED" : shortDescription = body
                break

            case "ACCEPTED" : shortDescription = body
                break

            default: shortDescription = body
                break

            return shortDescription
        }

        if(!"PENDING".equals(orders.paymentStatus.toUpperCase())) {
            title = "Payment Update"
            shortDescription = "Thank you for your payment of order ${orders.orderNo}"
        }

        Notification ntc = new Notification(
                userDevices: username,
                title: title,
                shortDescription: shortDescription,
                nType: "Transaction",
                content: order
        )
        notificationContentService.pushNotification(ntc)
    }

    /**
     * Push Notification, when update / chane assignee
     * @param orders
     * @return
     */
    def pushNotificationToAssignee(Orders orders) {
        JSON json = new JSON(orders)
        HashMap<String, Object> order = new ObjectMapper().readValue(json.toString(), HashMap.class)

        // get customer base on orders/checkout of product.
        def username = SecUser.findAllById(orders.assignTo).username
        Notification ntc = new Notification(
                userDevices: username,
                title: "Order Update",
                shortDescription: "This order ${orders.orderNo} at Avacass has been assigne to you",
                nType: "Transaction",
                content: order
        )
        notificationContentService.pushNotification(ntc)
    }
}
