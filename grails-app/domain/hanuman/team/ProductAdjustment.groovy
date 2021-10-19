package hanuman.team

class ProductAdjustment {

    Long productId
    String productName
    String description
    String imageUrl // item image

    Double adjustQty
    Date expiredDate
    String comment

    static belongsTo = [stockAdjustment: StockAdjustment]

    static constraints = {
        comment nullable: true
        description nullable: true
        expiredDate nullable: true
    }
}
