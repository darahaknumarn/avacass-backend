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
        String title = ""
        String shortDescription = ""
        JSON json = new JSON(orders)
        HashMap<String, Object> order = new ObjectMapper().readValue(json.toString(), HashMap.class)
        // get customer base on orders/checkout of product.
        def username = Customer.findAllById(orders.customerId).username
        title = "Order Update"
        String body = "Your order ${orders.orderNo} at Avacass has been updated ${orders.status}"

        switch (orders.status.toUpperCase()) {
            case "REJECT" : shortDescription = "${body} ${orders.rejectReason}"
                break

            case "DELIVERED" : shortDescription = "${body} ការបញ្ជាទិញរបស់អ្នក ${orders.orderNo} កំពង់ដឹកជញ្ជូនទៅ។"
                break

            case "ACCEPTED" : shortDescription = "${body} ការបញ្ជាទិញរបស់អ្នក​ ${orders.orderNo} បានទទួល។"
                break

            default: shortDescription = body
                break

            return shortDescription
        }

        if(!"PENDING".equals(orders.paymentStatus.toUpperCase())) {
            title = "Payment Update"
            shortDescription = "Thank you for your payment of order ${orders.orderNo} អរគុណសម្រាប់ការទូទាត់ប្រាក់​ របស់អ្នក។"
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
}
