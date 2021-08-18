package hanuman.team

import hanuman.team.base.BaseDomain

class OrderActivity extends BaseDomain{
    Long orderId
    String activityType
    String description
    Long changedId // current user
    String changedName // current user

    static constraints = {
        dateCreated nullable:true
        lastUpdated nullable:true
        description nullable:true ,sqlType:'text'
        lastUpdateBy nullable: true
        changedId nullable: true
        changedName nullable: true
    }
}
