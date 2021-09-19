package hanuman.team

import hanuman.team.base.BaseDomain

class StockBalance  extends BaseDomain{
    Long productId
    Float reserveQty
    Float stockBalance
    // warehouse as default = 1
    Long warehouseId = 1
    Date expiredDate

    static constraints = {
        lastUpdated nullable: true
        dateCreated nullable: true
        createdBy nullable: true
        lastUpdateBy nullable: true
        reserveQty  nullable: true

        expiredDate nullable: true
    }
}
