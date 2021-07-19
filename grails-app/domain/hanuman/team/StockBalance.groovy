package hanuman.team

import hanuman.team.base.BaseDomain

class StockBalance  extends BaseDomain{
//    Long productId
    Float reserveQty
    Float stockBalance

    static belongsTo = [product: Product]

    static constraints = {
        lastUpdated nullable: true
        dateCreated nullable: true
        createdBy nullable: true
        lastUpdateBy nullable: true
        reserveQty  nullable: true
    }
}
