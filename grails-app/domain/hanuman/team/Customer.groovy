package hanuman.team

class Customer {
    String name
    String email
    String phone
    String address1
    String address2
    String mobile
    String webSite
    String accountNumber
    Integer currencyId
    Integer cityId
    String fax
    String note

    String nameKh
    String vatTin
    String addressKh

//    static hasMany = [invoice:Invoice,customerPayment:CustomerPayment]
//    static belongsTo = [currency:Currency]
//    static hasOne = [city:City]


    static constraints = {
        name nullable: true
        email nullable: true
        phone nullable: true
        address1 nullable: true
        address2 nullable: true
        mobile nullable: true
        webSite nullable: true
        accountNumber nullable: true
        fax nullable: true
        note nullable: true
        note tyep:'text'
        currencyId nullable:true
        cityId nullable: true
        nameKh nullable: true
        vatTin nullable: true
        addressKh nullable: true
    }
}
