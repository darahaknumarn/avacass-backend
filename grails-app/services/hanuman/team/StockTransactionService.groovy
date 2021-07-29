package hanuman.team

import grails.gorm.transactions.Transactional
import javax.validation.constraints.NotNull

@Transactional
class StockTransactionService {

    // deduct stock of product by status "accepted"
    def deductStock(Orders order) {
        order.orderDetail.each { orderDetail ->
            def product = Product.get(orderDetail.productId)
            def sBalance = StockBalance.findByProduct(product)
            if (sBalance) {
                saveStockTransaction(TransactionType.ADJUST_STOCK.status, order.id, orderDetail, sBalance.stockBalance)

                sBalance.reserveQty -= orderDetail.qty // deducted of reserve qty
                sBalance.stockBalance = deductStockBalance(sBalance.stockBalance, sBalance.reserveQty)
                sBalance.save(flush: true)
            }
        }
    }

    private saveStockTransaction(String transactionType, Long orderId, OrderDetail orderDetail, Float stockBalance = null) {
        def sTransaction = new StockTransaction()
        if (transactionType == TransactionType.PURCHASE.status) {
            sTransaction.refId = orderId
            sTransaction.productId = orderDetail.productId
            sTransaction.transactionType = transactionType
            sTransaction.qty = orderDetail.qty
            sTransaction.beforeStockQty = 0
            sTransaction.afterStockQty = (sTransaction.qty - sTransaction.beforeStockQty)

        }else {
            sTransaction.refId = orderId
            sTransaction.productId = orderDetail.productId
            sTransaction.transactionType = transactionType
            sTransaction.qty = orderDetail.qty
            sTransaction.beforeStockQty = stockBalance
            sTransaction.afterStockQty = deductAfterStockQty(sTransaction.qty, sTransaction.beforeStockQty)
        }

        sTransaction.save(flush: true)
    }

    private Float deductAfterStockQty(@NotNull Double qty, @NotNull Float beforeStock) {
        return beforeStock - qty
    }

    // Deducted balance (stockBalance - reserveQty)
    private Float deductStockBalance(@NotNull Float stockBalance, @NotNull Float reserveQty) {
        return stockBalance - reserveQty
    }
}
