package hanuman.team

import grails.gorm.transactions.Transactional

@Transactional
class StockBalanceService {

    def updateStockBalance(Long productId) {
        def p = Product.get(productId)
        p.stockBalance= StockBalance.findAllByProductId(p.id).stockBalance.sum() ;
        p.save(flush:true)
    }
}
