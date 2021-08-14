package hanuman.team

import hanuman.team.base.BaseDomain

class Category  extends BaseDomain{
    String categoryName
    String imageUrl
    boolean isActive = true
    boolean isPopular = false
    static constraints = {
        dateCreated nullable: true
        lastUpdated nullable: true
        createdBy nullable:true
        lastUpdateBy nullable:true
        imageUrl nullable: true
    }
}
