package hanuman.team

import grails.converters.JSON
import hanuman.team.base.BaseDomain

class OrderDetail extends BaseDomain{
    Long productId
    String productName
    String description
    Long categoryId
    String categoryName
    String imageUrl // item image

    Double qty
    Double price
    Double amount
    Integer vendorId
    String vendorName
    Boolean isDeleted = false

    static belongsTo = [orders: Orders]

    static constraints = {
        description sqlType:'text' , nullable:true
        imageUrl nullable: true

        dateCreated nullable:true
        lastUpdated nullable:true
        createdBy nullable:true
        lastUpdateBy nullable:true
        vendorName nullable: true
        vendorId nullable: true

    }
    static {
        JSON.registerObjectMarshaller(this, { OrderDetail order ->
            Map result = new LinkedHashMap(order.properties)
            result.id = order.id
            def product = Product.get(order.productId)
            result.barcode = product?.barcode
            result.locationCode = product?.binLocationCode
            result.remove("orders")
            return result
        })
    }
}
