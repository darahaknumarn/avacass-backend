package hanuman.team

class ProductImage {
    Boolean isBaseImage
    String thumbnailUrl
    String mediumUrl
    String largeUrl
    String description
    static belongsTo = [product : Product]
    static constraints = {
        description nullable:true , sqlType:'text'
        thumbnailUrl nullable: true
        mediumUrl nullable:  true
        largeUrl nullable:true
        isBaseImage nullable:true
    }
}
