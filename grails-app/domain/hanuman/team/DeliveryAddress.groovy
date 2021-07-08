package hanuman.team

import hanuman.team.base.BaseDomain

class DeliveryAddress extends BaseDomain{
    String address
    String note
    String street
    String houseNo
    String phone
    Boolean isDefault = true
    String label
    String icon
    Double latitude
    Double longitude
    static belongsTo = [customer:Customer]
    
    static constraints = {
        latitude nullable:true
        longitude nullable:true
        dateCreated nullable:true
        lastUpdated nullable:true
        createdBy nullable:true
        lastUpdateBy nullable:true

        address nullable:true, sqlType: 'text'
        note nullable:true
        street nullable:true
        houseNo nullable:true
        phone nullable:true
        isDefault nullable:true
        label nullable: true
        icon nullable: true

    }
}
