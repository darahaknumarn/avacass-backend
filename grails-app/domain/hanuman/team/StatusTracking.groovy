package hanuman.team

import hanuman.team.base.BaseDomain

class StatusTracking extends BaseDomain{
    Long orderId
    Long changedId
    String changedName
    String activity // adding text of activity every change status

    static constraints = {
        changedId nullable: true
        changedName nullable: true
        lastUpdated nullable: true
        dateCreated nullable: true
        createdBy nullable: true
        lastUpdateBy nullable: true
    }
}
