package hanuman.team

class VendorCategory {
    static belongsTo = [category : Category , vendor : Vendor]
    static constraints = {
    }
}
