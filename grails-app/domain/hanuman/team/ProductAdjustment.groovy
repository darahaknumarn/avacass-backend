package hanuman.team

import grails.converters.JSON

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

    static {
        JSON.registerObjectMarshaller(this, { ProductAdjustment pa ->
            Map result = new LinkedHashMap(pa.properties)
            def sBalance = StockBalance.findAllByProductId(pa.productId).stockBalance.sum()
            result.id = pa.id
            result.stockBalance = sBalance?sBalance: 0.0
            return result
        })
    }
}
