package hanuman.team

import hanuman.team.base.BaseDomain

class Vendor extends BaseDomain {
    String name
    String description
    Boolean isPopular = false
    String imageUrl

    static constraints = {
        name nullable:true
        dateCreated nullable:true
        lastUpdated nullable:true
        createdBy nullable:true
        lastUpdateBy nullable:true
        description sqlType:'text' , nullable:true
        imageUrl nullable: true
    }
}
