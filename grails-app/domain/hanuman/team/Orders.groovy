package hanuman.team

import hanuman.team.base.BaseDomain

class Orders extends BaseDomain {

    Integer customerId
    String customerName
    Integer deliveryAddressId
    String orderNo
    String description
    String paymentStatus  // get From Enum
    Boolean isFullfilled // mean for package
    String deliveryMethod // get from Enum
    Date deliveryTime
    Double subTotal
    Double perVat // vat percentage
    Double vat // vat amount
    Double total
    Boolean isSentMail
    Double totalQty
    Integer deliveryScheduleId
    Double deliveryFee
    Boolean isEditable
    Boolean isDeleted
    static  hasMany = [orderDetail: OrderDetail]

    static constraints = {
        dateCreated nullable:true
        lastUpdated nullable:true
        orderDetail cascade: "all-delete-orphan"

        createdBy nullable:true
        lastUpdateBy nullable:true
    }
}
