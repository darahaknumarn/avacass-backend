package hanuman.team

import hanuman.team.base.BaseDomain

class Customer extends BaseDomain {
    String code
    String firstName
    String lastName
    String phone
    String email
    String image
    String remark
    Boolean isSubscribed =false
    String username

    static hasMany = [deliveryAddress:DeliveryAddress]


    static constraints = {
        firstName nullable: true
        lastName nullable: true
        phone nullable: true
        email nullable:  true
        image nullable:  true
        remark sqlType :'text' , nullable: true
        isSubscribed nullable: true
        username nullable: true

        deliveryAddress cascade: "all-delete-orphan"

        dateCreated nullable: true
        lastUpdated nullable: true
        createdBy nullable:true
        lastUpdateBy nullable:true
    }
}
