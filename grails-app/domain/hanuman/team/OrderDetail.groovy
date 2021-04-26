package hanuman.team

import hanuman.team.base.BaseDomain

class OrderDetail extends BaseDomain{
    Long productId
    String productName
    String description
    Long categoryId
    String categoryName
    String imageUrl // item image

    Double qty
    Double price
    Double amount

    Boolean isDeleted = false

    static belongsTo = [orders: Orders]

    static constraints = {
        description sqlType:'text' , nullable:true
        imageUrl nullable: true

        dateCreated nullable:true
        lastUpdated nullable:true
        createdBy nullable:true
        lastUpdateBy nullable:true

    }
}
