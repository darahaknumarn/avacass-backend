package hanuman.team

import grails.gorm.transactions.Transactional

@Transactional
class DeliveryAddressService {

    def updateDeliveryAddressDefault(Customer customer, long id) {
        def defaultAdd
        defaultAdd = DeliveryAddress.findAllByCustomerAndIsDefaultAndIdNotEqual(customer,true, id)
        if (defaultAdd) {
            defaultAdd.iterator().each { adds ->
                defaultAdd = DeliveryAddress.findById(adds.id)
                defaultAdd.isDefault = false

                defaultAdd.save(flush: true)
            }
        }
    }
}
