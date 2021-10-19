package hanuman.team

import grails.converters.JSON

class StockAdjustment {

    String note
    Date adjustDate = new Date()
    String reason
    String adjustBy
    static hasMany = [productAdjustments: ProductAdjustment]

    static constraints = {
        reason nullable: true
        note nullable: true
        adjustBy nullable: true
        productAdjustments cascade: "all-delete-orphan"
    }

    static {
        JSON.registerObjectMarshaller(this, { StockAdjustment st ->
            Map result = new LinkedHashMap(st.properties)
            result.id = st.id
            return result
        })
    }
}
