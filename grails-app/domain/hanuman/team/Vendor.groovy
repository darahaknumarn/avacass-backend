package hanuman.team

class Vendor {
    String name

    static hasMany = [purchase:Purchase]
    static constraints = {
        name nullable:true
    }
}
