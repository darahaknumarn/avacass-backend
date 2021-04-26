package hanuman.team

import hanuman.team.base.BaseDomain

class Category  extends BaseDomain{
    String categoryName
    boolean isActive = true
    static constraints = {
        dateCreated nullable: true
        lastUpdated nullable: true
        createdBy nullable:true
        lastUpdateBy nullable:true

    }
}
