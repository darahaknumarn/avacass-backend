package hanuman.team

import grails.gorm.transactions.Transactional

@Transactional
class OrderActivityService {

    def addActivity(Long userId, String username, OrderActivityType activityType, String description, Long orderId) {
        def orderActivity = new OrderActivity()
        orderActivity.activityType  = activityType.toString()
        orderActivity.description = description
        orderActivity.createdBy = username
        orderActivity.orderId = orderId
        orderActivity.changedName = username
        orderActivity.changedId = userId

        orderActivity.save(flush:true)
    }

}
