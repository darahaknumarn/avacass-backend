package hanuman.team

import com.fasterxml.jackson.databind.ObjectMapper
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import hanuman.notification.Notification
import hanuman.security.SecUser
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.StatusCode

import java.util.stream.Collectors

@Transactional
class OrderService {

    def notificationContentService
    def applicationConfigurationService
    def translateService
    /**
     * Notify to customer, when admin update/changed status to "Accepted"
     * @param order
     * @return
     */
    def pushNotification(Orders orders, String status) {
        JSON json = new JSON(orders)
        HashMap<String, Object> order = new ObjectMapper().readValue(json.toString(), HashMap.class)
        // get customer base on orders/checkout of product.
        def username = Customer.findAllById(orders.customerId).username
        String title = "Order Update"
        String body = translateService.translateTo(status, orders.language)

        Notification ntc = new Notification(
                userDevices: username,
                title: title,
                shortDescription: body.replace("{#}", orders.orderNo),
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

    /**
     * update status accepted, delivered, Reject change close to 'true'
     * @param status
     */
    def updateOrderComplete(Orders order) {
        def completeOrderStatus = applicationConfigurationService.getApplicationConfigurationByNameAndIsActive("CompleteOrderStatus")
        if (!completeOrderStatus) {
            return order
        }

        def val = JSON.parse(completeOrderStatus?.value)
        // search in list of order status.
        Integer indexOf = val.findIndexOf { cos -> cos.name.toUpperCase() == order.status.toUpperCase()}
        if (indexOf != -1)
            order.isClosed = true

        return order
    }

    /**
     * Push Notification, when finish order
     * @param orders
     * @return
     */
    def pushNotificationFinishOrder(Orders orders) {
        JSON json = new JSON(orders)
        HashMap<String, Object> order = new ObjectMapper().readValue(json.toString(), HashMap.class)

        // get customer base on orders/checkout of product.
        def username =  Customer.findAllById(orders.customerId).username
        Notification ntc = new Notification(
                userDevices: username,
                title: "Purchase Order",
                shortDescription: "Your order ${orders.orderNo} at Avacass has been ${orders.status}",
                nType: "Transaction",
                content: order
        )
        notificationContentService.pushNotification(ntc)
    }

}
