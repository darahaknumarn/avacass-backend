package hanuman.team

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import hanuman.ApplicationConfiguration
import hanuman.team.base.BaseDomain

class Product extends BaseDomain{
    Long categoryId
    String categoryName
    String title
    String description
    Long vendorId
    String vendorName
    Double price
    Double compareAtPrice = 0.0
    Double cost = 0.0
    Double rate = 0.0
    Boolean isChargeTax = false
    String sku
    Boolean isTrackingQty
    Boolean isSellOutOfStock
    Double margin = 0.0
    Boolean status
    String imageUrl
    Boolean isTopsSale = false
    Boolean isRecommend  = false

    String barcode
    Date expiredDate
    String supplier
    String binLocationCode

    // UoM
    String uomName
    Long uomId

    // product qty
    Float reOrderPoint
    Float minimumStockQty
    Float availableStockQty
    Float stockBalance=0


    static hasMany = [productImage:ProductImage, relatedProduct: RelatedProduct]

    static constraints = {
        createdBy nullable:true
        lastUpdateBy nullable:true
        dateCreated nullable:true
        lastUpdated nullable:true

        description sqlType:'text', nullable: true
        vendorId nullable: true
        vendorName nullable:true
        isChargeTax nullable:true
        sku nullable:true
        imageUrl nullable:true
        productImage cascade: "all-delete-orphan"
        relatedProduct cascade: "all-delete-orphan"

        barcode nullable: true
        expiredDate nullable: true
        supplier nullable: true
        binLocationCode nullable: true
        stockBalance nullable: true
    }

    static {
//        JSON.registerObjectMarshaller(this, { Product pr ->
//            Map result = new LinkedHashMap(pr.properties)
//            def sBalance = StockBalance.findAllByProductId(pr.id).stockBalance.sum()
//            result.id = pr.id
//            result.stockBalance = sBalance?sBalance: 0.0
//
//            return result
//        })
    }
}
