package hanuman.team

class StockTransaction {
    String transactionType  // "Purchase" , "AdjustStock"
    String refCode
    Long productId
    Long qty

    static constraints = {

    }
}
