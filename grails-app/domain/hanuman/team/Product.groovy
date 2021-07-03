package hanuman.team

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
    static hasMany = [productImage:ProductImage]
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
    }
}
