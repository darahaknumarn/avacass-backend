package hanuman.team

import grails.converters.JSON
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import hanuman.security.Role
import hanuman.team.base.BaseDomain

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class Customer extends BaseDomain implements Serializable {
    transient springSecurityService
    private static final long serialVersionUID = 1
    String code // next code
    String firstName
    String lastName
    String phone
    String email
    String image
    String remark
    Date dob
    String gender
    Boolean isSubscribed =false
    String language
    //security
    String username
    //@ValidPassword
    String password
    boolean enabled = true
    boolean accountExpired = false
    boolean accountLocked = false
    boolean passwordExpired = false

    static hasMany = [deliveryAddress:DeliveryAddress]

    Set<Role> getAuthorities() {
        null as Set<Role>
    }

    Customer(String username, String password) {
        this()
        this.username = username
        this.password = password
    }
    @Override
    int hashCode() {
        username?.hashCode() ?: 0
    }
    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : springSecurityService.encodePassword(password)
    }

    static transients = ['springSecurityService']


    @Override
    boolean equals(other) {
        is(other) || (other instanceof Customer && other.username == username)
    }

    @Override
    String toString() {
        username
    }

    static constraints = {
        gender nullable: true
        language nullable: true
        firstName nullable: true
        lastName nullable: true
        phone nullable: true, unique: true
        email nullable:  true
        image nullable:  true
        remark sqlType :'text' , nullable: true
        dob nullable: true
        isSubscribed nullable: true
        deliveryAddress cascade: "all-delete-orphan"
        dateCreated nullable: true
        lastUpdated nullable: true
        createdBy nullable:true
        lastUpdateBy nullable:true
        password nullable: false, blank: false, password: true , bindable: false
        username nullable: false, blank: false, unique: true

    }

    static mapping = {
        password column: '`password`'
    }

    static {
        JSON.registerObjectMarshaller(this, { Customer cus ->
            Map result = new LinkedHashMap(cus.properties)
            result.id = cus.id
            result.deliveryAddress = DeliveryAddress.findAllByCustomer(cus)
            return result
        })
    }
}
