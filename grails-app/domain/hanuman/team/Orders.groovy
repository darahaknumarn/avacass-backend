package hanuman.team

import grails.converters.JSON
import hanuman.team.base.BaseDomain

class Orders extends BaseDomain {
    Long cartId
    Long customerId
    String customerName
    Integer deliveryAddressId
    String orderNo
    String description
    String paymentStatus  = PaymentStatus.PENDING.status
    Boolean isFullFilled = false
    String deliveryMethod
    Date deliveryTime // on schedule
    Double subTotal
    Double perVat // default 10
    Double vat
    Double total
    Boolean isSentMail = false
    Double totalQty
    Integer deliveryScheduleId
    Double deliveryFee
    Boolean isEditable = false
    Boolean isDeleted = false
    Boolean isClosed = false
    String status = OrderStatus.Accepted.desc
    Integer assignTo

    String rejectReason
    Date rejectDate
    String rejectBy

    static  hasMany = [orderDetail: OrderDetail]

    static constraints = {
        cartId nullable: true
        status nullable: true
        dateCreated nullable:true
        lastUpdated nullable:true
        orderDetail cascade: "save-update"
        description nullable: true

        createdBy nullable:true
        lastUpdateBy nullable:true

        rejectReason nullable: true
        rejectBy nullable: true
        rejectDate nullable: true
        assignTo nullable:  true 
    }

    static {
        JSON.registerObjectMarshaller(this, { Orders order ->
            Map result = new LinkedHashMap(order.properties)
            result.id = order.id
            def customer =  Customer.get(order.customerId)
            result.customerName = customer?.phone
            result.phoneNumber = customer?.phone
            result.deliveryAddress = DeliveryAddress.get(order.deliveryAddressId)
            return result
        })
    }
}
