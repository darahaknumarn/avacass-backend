package hanuman.team

import hanuman.team.base.BaseDomain

class StockBalance  extends BaseDomain{
    Float reserveQty
    Float stockBalance
    // warehouse as default = 1
    Long warehouseId = 1
    Date expiredDate

    static belongsTo = [product: Product]

    static constraints = {
        lastUpdated nullable: true
        dateCreated nullable: true
        createdBy nullable: true
        lastUpdateBy nullable: true
        reserveQty  nullable: true

        expiredDate nullable: true
    }
}
