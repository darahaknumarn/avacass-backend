package hanuman.team

import grails.converters.JSON

class Purchase {
    String code
    Long vendorId
    String vendorName
    Date purchaseDate = new Date()
    String note

    // sum purchase detail's qty
    Float totalQty

    // status of purchase
    String status

    static hasMany = [purchaseDetails: PurchaseDetail]

    static constraints = {
        note nullable: true
        purchaseDetails cascade: "all-delete-orphan"
    }

    static {
        JSON.registerObjectMarshaller(this, { Purchase purchase ->
            Map result = new LinkedHashMap(purchase.properties)
            result.id = purchase.id
            return result
        })
    }
}
