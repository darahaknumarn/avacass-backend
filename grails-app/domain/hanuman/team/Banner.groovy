package hanuman.team

class Banner {

    String name
    String imageUrl
    String link
    Integer orderNumber
    Boolean isActive = true

    static constraints = {
        link nullable: true
        orderNumber nullable: true
    }
}
