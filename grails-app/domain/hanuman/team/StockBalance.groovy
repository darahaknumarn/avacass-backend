package hanuman.team

import hanuman.team.base.BaseDomain

class StockBalance  extends BaseDomain{
    Long productId
    Double availableQty
    Float reserveQty
    Float stockBalance
    static constraints = {
         lastUpdated nullable: true
         dateCreated nullable: true
         createdBy nullable: true
         lastUpdateBy nullable: true

    }
}
