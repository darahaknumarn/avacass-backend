package hanuman.team

import grails.gorm.transactions.Transactional

@Transactional
class StatusTrackingService {

    def addStatusTracking(Orders order, String status, String changedName, Long changedId) {
        def st = new StatusTracking()
        st.orderId = order.id
        st.changedName = changedName
        st.changedId = changedId
        st.activity = "Changed status from ${status} to ${order.status}"

        st.save(flush: true)
    }
}
