package hanuman.team

class StockTransaction {
    String transactionType  // "Purchase" , "AdjustStock", "Sale"
    String refId // order id
    Long productId
    Double qty
//    Float beforeStockQty
//    Float afterStockQty

    static constraints = {
        refId nullable: true
//        beforeStockQty nullable: true
//        afterStockQty nullable: true
    }
}
