package hanuman.team

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.RespondDTO
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import hanuman.simplegenericrestfulcontroller.generic.StatusCode

class StockAdjustmentController extends SimpleGenericRestfulController<StockAdjustment>{

    def stockTransactionService

    StockAdjustmentController() {
        super(StockAdjustment)
    }

    @Transactional
    @Override
    def saveAction() {
        def stockAdj = new StockAdjustment()
        def requestJson = request.JSON
        bindData(stockAdj, requestJson)

        // before save action
        stockAdj = beforeSave(stockAdj)

        if (stockAdj.getClass() == RespondDTO) {
            transactionStatus.setRollbackOnly()
            render stockAdj as JSON
            return
        }
        stockAdj.adjustBy = getPrincipal().username
        // validation object of stock adjustment
        stockAdj.validate()
        if (stockAdj.hasErrors()) {
            transactionStatus.setRollbackOnly()
            render  JSONFormat.respond(null , StatusCode.Invalid ,getError(stockAdj)) as JSON
            return
        }

        // stock adjust for stock balance
        stockTransactionService.stockAdjustment(stockAdj)

        stockAdj.save(flush:true)
        render  JSONFormat.respond(stockAdj , StatusCode.OK) as JSON
        return stockAdj
    }

    @Override
    def delete() {
        def stockAdj = StockAdjustment.get(params.long('id'))

        stockAdj.productAdjustments.each { proAdj ->
             Date expDate = proAdj.expiredDate?.clearTime()
            // find stock balance
            def stb = StockBalance.findByProductIdAndExpiredDate(proAdj.productId, expDate)
            if (stb) {
                stb.stockBalance -= proAdj.adjustQty
                stb.save(flush: true)
            }
        }
        stockAdj.save(flush:true )
        respond JSONFormat.respondSingleObject(stockAdj)
    }
}
