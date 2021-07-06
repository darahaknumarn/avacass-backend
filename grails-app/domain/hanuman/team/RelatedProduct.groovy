package hanuman.team

import hanuman.team.base.BaseDomain

class RelatedProduct extends BaseDomain{
    Long relatedProductId
    static belongsTo = [product: Product]

    static constraints = {
        dateCreated nullable: true
        lastUpdated nullable: true
        createdBy nullable:true
        lastUpdateBy nullable:true

        relatedProductId nullable: true

    }
}
