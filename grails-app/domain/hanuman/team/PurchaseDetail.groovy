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

    static belongsTo = [purchase: Purchase]
    static constraints = {
    }
}
