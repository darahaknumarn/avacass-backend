package hanuman.team

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.RespondDTO
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import hanuman.simplegenericrestfulcontroller.generic.StatusCode

class StockAdjustmentController extends SimpleGenericRestfulController<StockAdjustment>{

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

        // validation object of stock adjustment
        stockAdj.validate()
        if (stockAdj.hasErrors()) {
            transactionStatus.setRollbackOnly()
            render  JSONFormat.respond(null , StatusCode.Invalid ,getError(stockAdj)) as JSON
            return
        }

        // stock adjust for stock balance

        stockAdj.save(flush:true)
        render  JSONFormat.respond(stockAdj , StatusCode.OK) as JSON
        return stockAdj
    }
}
