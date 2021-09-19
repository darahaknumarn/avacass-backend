package hanuman.team

class PurchaseDetail {
    Long productId
    String productName
    String imageUrl
    String description
    String uomName
    String uomId
    Long categoryId
    String categoryName

    // adding qty
    Float qty

    // Set default
    Date expiredDate
    Long warehouseId = 1

    static belongsTo = [purchase: Purchase]
    static constraints = {
        expiredDate nullable: true
        warehouseId nullable: true
    }
}
