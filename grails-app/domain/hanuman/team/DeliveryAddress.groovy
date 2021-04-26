package hanuman.team

import hanuman.team.base.BaseDomain

class DeliveryAddress extends BaseDomain{
    String name

    String firstName
    String lastName
    Long countryId
    String countryName
    Long cityId
    String cityName
    Long districtId
    String districtName
    Long communeId
    String communeName
    String street
    String houseNo
    String phone
    Boolean isDefault
    static belongsTo = [customer:Customer]
    
    static constraints = {
        dateCreated nullable:true
        lastUpdated nullable:true
        createdBy nullable:true
        lastUpdateBy nullable:true

        name nullable:true           
        firstName nullable:true
        lastName nullable: true
        countryId nullable:true
        countryName nullable:true
        cityId nullable:true
        cityName nullable:true
        districtId nullable:true
        districtName nullable:true
        communeId nullable:true
        communeName nullable:true
        street nullable:true
        houseNo nullable:true
        phone nullable:true
        isDefault nullable:true

    }
}
