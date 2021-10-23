package hanuman.team

import grails.gorm.transactions.Transactional
import javax.validation.constraints.NotNull

@Transactional
class StockTransactionService {

    // Every order has been deduct stock of product.
    def deductStock(Orders order) {
        def sBalance = null
        order.orderDetail.each { orderDetail ->
            // Find stock balance product nearly expire date.
            sBalance = checkProductExpiredDate(orderDetail.productId)
            if (sBalance) {
                sBalance.stockBalance = deductStockBalance(sBalance.stockBalance, orderDetail.qty)
            }else {
                sBalance = checkProductEmptyExpiredDate(orderDetail.productId)
                sBalance.stockBalance = deductStockBalance(sBalance.stockBalance, orderDetail.qty)
            }

            // save stock balance
            saveTransaction(TransactionType.ADJUST_STOCK.status, order.id, orderDetail.productId, orderDetail.qty)
            sBalance.save(flush: true)
        }
    }

    // Order has return stock of product, when status reject.
    def returnStockBalance(Orders order) {
        order.orderDetail.each { orderDetail ->
            def sBalance = checkProductExpiredDate(orderDetail.productId)
            if (sBalance) {
                sBalance.stockBalance = returnStockQty(sBalance.stockBalance, orderDetail.qty)
            }else {
                // Expired date is empty
                sBalance = StockBalance.findByProductId(orderDetail.productId)
                sBalance.stockBalance = returnStockQty(sBalance.stockBalance, orderDetail.qty)
            }

            // record in stock transaction
            sBalance.save(flush: true)
            saveTransaction(TransactionType.SALE_RETURN.status, order.id, orderDetail.productId, orderDetail.qty)
        }
    }

    // Check expired date of product or item in stock balance.
    private StockBalance checkProductExpiredDate(Long productId) {
        def sBalance = null
        def stockBalance = StockBalance.createCriteria().list {
            eq("productId", productId)
            isNotNull("expiredDate")
            order("expiredDate", "asc")
        }
        // Check expired date less than
        if (stockBalance) {
            stockBalance[0].each { sb -> sBalance = sb}
            return sBalance
        }

        return null
    }

    // Check empty expired date of product or item in stock balance.
    private StockBalance checkProductEmptyExpiredDate(Long productId) {
        def stockBalance = null
        // Check empty expired date
        stockBalance = StockBalance.findByProductIdAndExpiredDateIsNull(productId)
        if (!stockBalance)
            return null

        return stockBalance
    }

    // Save stock transaction of all product or item.
    private saveTransaction(String transactionType, Long orderId, Long productId, Double qty) {
        def sTransaction = new StockTransaction()
        sTransaction.refId = orderId
        sTransaction.productId = productId
        sTransaction.transactionType = transactionType
        sTransaction.qty = qty
        sTransaction.save(flush: true)
    }

    // Save stock transaction of all product or item.
    private saveStockTransaction(String transactionType, Long orderId, Long productId, Double qty, Float stockBalance = null) {
        def sTransaction = new StockTransaction()
        if (transactionType == TransactionType.PURCHASE.status) {
            sTransaction.refId = orderId
            sTransaction.productId = productId
            sTransaction.transactionType = transactionType
            sTransaction.qty = qty
            sTransaction.beforeStockQty = 0
            sTransaction.afterStockQty = (sTransaction.qty - sTransaction.beforeStockQty)

        }else {
            // adjust stock or sale return
            sTransaction.refId = orderId
            sTransaction.productId = productId
            sTransaction.transactionType = transactionType
            sTransaction.qty = qty
            sTransaction.beforeStockQty = stockBalance
            if (transactionType.equals(TransactionType.SALE_RETURN.status))
                sTransaction.afterStockQty = returnStockQty(sTransaction.qty, sTransaction.beforeStockQty)
            else
                sTransaction.afterStockQty = deductAfterStockQty(sTransaction.qty, sTransaction.beforeStockQty)
        }

        sTransaction.save(flush: true)
    }

    private Float deductAfterStockQty(@NotNull Double qty, @NotNull Float beforeStock) {
        return beforeStock - qty
    }

    // Deducted balance (stockBalance - orderQty)
    private Float deductStockBalance(@NotNull Float stockBalance, @NotNull Double orderQty) {
        return stockBalance - orderQty
    }

    // Return stock balance (stockBalance + orderQty)
    private Float returnStockQty(@NotNull Double stockBalance, @NotNull Double orderQty) {
        return stockBalance + orderQty
    }

    /**
     * Purchase stock balance base on expireDate, product and warehouse
     * Purchase on purchase operation
     */
    def purchaseStockBalance(Purchase purchase) {
        purchase.purchaseDetails.each { pur ->
            // save stock transaction
            saveTransaction(TransactionType.PURCHASE.status, pur.id, pur.productId, pur.qty)

            // compare condition
            def sBalance = validStockBalance(pur)
            if (sBalance)
                // update stock balance
                setStockBalance(sBalance, pur)
            else
                // register new stock balance
                registerStockBalance(pur)
        }
    }

    /**
     * Check stock balance as expireDate, product and warehouseId
     * @param purchase
     * @return
     */
    private StockBalance validStockBalance(PurchaseDetail purchase) {
        Date expDate = purchase.expiredDate.clearTime()
        // Check & getting product as obj
        def sBalance = StockBalance.findByProductIdAndExpiredDateBetweenAndWarehouseId(purchase.productId, expDate, expDate.plus(1), purchase.warehouseId)
        if (!sBalance)
            return null

        return sBalance
    }

    /**
     * Set / update stock balance
     * @param sBalance
     * @param purchase
     * @return
     */
    private setStockBalance(StockBalance sBalance, PurchaseDetail purchase) {
        sBalance.stockBalance += purchase.qty
        sBalance.save(flush: true)
    }

    /**
     * Register / save new stock balance
     * @param purchase
     * @return
     */
    private registerStockBalance(PurchaseDetail purchase) {
        def sBalance = new StockBalance()
        sBalance.productId = purchase.productId
        sBalance.stockBalance = purchase.qty
        sBalance.warehouseId = purchase.warehouseId
        sBalance.expiredDate = purchase.expiredDate

        sBalance.save(flush: true)
    }

    def stockAdjustment(StockAdjustment stocks) {
        stocks.productAdjustments.each { pa ->
            Date expDate = pa.expiredDate.clearTime()
            // find stock balance
            def stb = StockBalance.findByProductIdAndExpiredDateBetween(pa.productId, expDate, expDate.plus(1))
            if (stb) {
                // adjust stock balance
                stb.stockBalance += pa.adjustQty
                stb.save(flush: true)
            }
        }

    }
}
