package hanuman.team

import grails.gorm.transactions.Transactional

@Transactional
class OrderActivityService {


    def addActivity(Long userId, OrderActivityType activityType, String description, Long orderId) {
        def orderActivity = new OrderActivity()
        orderActivity.activityType  = activityType.toString()
        orderActivity.description = description
        orderActivity.createdBy = userId
        orderActivity.orderId = orderId
        orderActivity.save(flush:true)
    }


}
