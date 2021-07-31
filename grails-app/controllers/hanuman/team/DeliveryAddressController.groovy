package hanuman.team

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.RespondDTO
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import hanuman.simplegenericrestfulcontroller.generic.StatusCode

class DeliveryAddressController extends SimpleGenericRestfulController<DeliveryAddress>{

    def deliveryAddressService
    DeliveryAddressController() {
        super(DeliveryAddress)
    }

    @Override
    def index(PaginationCommand paginationCommand) {
        def da = DeliveryAddress.createCriteria().list(paginationCommand.params) {
            customer {
                eq("id", params.long("customerId"))
            }
        }

        render JSONFormat.respond(da, StatusCode.OK) as JSON
    }

    @Transactional
    @Override
    def saveAction(){
        def address = new DeliveryAddress()
        def requestJson = request.JSON

        bindData(address, requestJson)
        address = beforeSave(address)

        if (address.getClass() == RespondDTO) {
            transactionStatus.setRollbackOnly()
            render address as JSON
            return
        }

        address.validate()

        if (address.hasErrors()) {
            transactionStatus.setRollbackOnly()
            render  JSONFormat.respond(null , StatusCode.Invalid ,getError(address)) as JSON
            return
        }

        // Check unique label base customer
        def add = DeliveryAddress.findAllByCustomerAndLabel(address.customer, address.label)
        if (add) {
            render JSONFormat.respond(null , StatusCode.Invalid, "Label has been already exist.") as JSON
            return
        }

        // Check is default address : true, then update default to false
        def defaultAdd = DeliveryAddress.findAllByCustomerAndIsDefault(address.customer,true)
        if (defaultAdd)
            address.isDefault = false

        address.save(flush:true)
        render  JSONFormat.respond(address , StatusCode.OK) as JSON
    }

    @Transactional
    @Override
    def update(){
        DeliveryAddress deliveryAddress = DeliveryAddress.get(params.id)
        if(!deliveryAddress){
            notFound()
            return
        }

        def requestJson = request.JSON
        bindData(deliveryAddress, requestJson)

        deliveryAddress.validate()
        if (deliveryAddress.hasErrors()){
            transactionStatus.setRollbackOnly()
            render JSONFormat.respond(null , StatusCode.Invalid ,getError(deliveryAddress)) as JSON
            return
        }

        // Check unique label base customer
        def add = DeliveryAddress.findAllByCustomerAndLabelAndIdNotEqual(deliveryAddress.customer, deliveryAddress.label, params.id)
        if (add) {
            render JSONFormat.respond(null , StatusCode.Invalid, "Label has been already exist.") as JSON
            return
        }

        // update default delivery address.
        deliveryAddressService.updateDeliveryAddressDefault(deliveryAddress.customer, Long.parseLong(params.id))

        deliveryAddress.save(flush:true)
        render JSONFormat.respond(deliveryAddress) as JSON
    }
}
