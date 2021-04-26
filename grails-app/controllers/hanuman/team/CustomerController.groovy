package hanuman.team

import grails.converters.JSON
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

        }
        respond JSONFormat.respond(customerList)
    }
    @Override
    def show() {

        def data =Customer.get(params.id).properties
        respond JSONFormat.respondSingleObject(data)
    }

    @Override
    def beforeSave(Customer customer){
        customer.code = nextCodeService.getLastCode("Customer" , true , [:])
        return  customer
    }

}
