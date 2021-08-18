package hanuman.team

import hanuman.team.base.BaseDomain

class OrderActivity extends BaseDomain{
    Integer orderId
    String activityType
    String description

    static constraints = {
        dateCreated nullable:true
        lastUpdated nullable:true
        description nullable:true ,sqlType:'text'
        lastUpdateBy nullable: true
    }
}
