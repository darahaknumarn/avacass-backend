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
    Double compareAtPrice
    Double cost
    Double rate
    Boolean isChargeTax = false
    String sku
    Boolean isTrackingQty
    Boolean isSellOutOfStock
    Double margin
    Boolean status
    String imageUrl
    Boolean isTopsSale = false
    Boolean isRecommend  = false

    String barcode
    Date expiredDate
    String supplier
    String binLocationCode
    static hasMany = [productImage:ProductImage, relatedProduct: RelatedProduct]

    static hasOne = [stockBalance: StockBalance]
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
        stockBalance cascade: "all-delete-orphan"

        barcode nullable: true
        expiredDate nullable: true
        supplier nullable: true
        binLocationCode nullable: true
    }

    static {
        JSON.registerObjectMarshaller(this, { Product pr ->
            Map result = new LinkedHashMap(pr.properties)
            result.id = pr.id
            return result
        })
    }
}
