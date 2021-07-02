package hanuman.team

class StockTransaction {
    String transactionType  // "Purchase" , "AdjustStock"
    String refId // order id
    Long productId
    Double qty
    Float beforeStockQty
    Float afterStockQty

    static constraints = {

    }
}
