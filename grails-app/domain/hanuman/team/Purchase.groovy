package hanuman.team

class Purchase {
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
}
