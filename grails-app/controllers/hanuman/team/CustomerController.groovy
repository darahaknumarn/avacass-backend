package hanuman.team

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import hanuman.simplegenericrestfulcontroller.generic.StatusCode


class CustomerController extends SimpleGenericRestfulController<Customer>{

    def nextCodeService
    CustomerController(){
        super(Customer)
    }
    @Override
    def index(PaginationCommand paginationCommand){
        def customerList =Customer.createCriteria().list(paginationCommand.params){
            if(params.code){
                like('code' , "%${params.code}%")
            }
            if(params.phone){
                like('phone' , "%${params.phone}%")
            }
            if(params.email){
                like('email' , "%${params.email}%")
            }
            if(params.username){
                like('username' , "%${params.username}%")
            }

        }
        respond JSONFormat.respond(customerList)
    }

    @Override
    def beforeSave(Customer customer){
        customer.code = nextCodeService.getLastCode("Customer" , true , [:])
        customer.password = (customer.password+customer.username)
        return  customer
    }

    @Transactional
    @Override
    def updateAction() {
        def cus = Customer.get(params.long("id"))
        if (cus == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        def requestJson = request.JSON
        bindData(cus, requestJson)
        cus.validate()
        if (cus.hasErrors()) {
            transactionStatus.setRollbackOnly()
            render JSONFormat.respond(null, StatusCode.Invalid, getError(cus)) as JSON
            return
        }
        if (cus.id != getPrincipal().id) {
            render JSONFormat.respond(null, StatusCode.Invalid, getError(cus)) as JSON
            return
        }

        cus.save(flush: true)
        render JSONFormat.respondSingleObject(cus) as JSON
    }

}
