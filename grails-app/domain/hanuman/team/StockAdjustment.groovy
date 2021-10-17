package hanuman.team

class StockAdjustment {

    String note
    Date adjustDate = new Date()
    String reason

    static hasMany = [productAdjustments: ProductAdjustment]

    static constraints = {
        reason nullable: true
        note nullable: true
        productAdjustments cascade: "all-delete-orphan"
    }
}
