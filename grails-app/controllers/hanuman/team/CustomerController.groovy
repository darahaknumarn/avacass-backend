package hanuman.team

import grails.converters.JSON
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import hanuman.simplegenericrestfulcontroller.generic.StatusCode


class CustomerController extends SimpleGenericRestfulController<Customer>{
    CustomerController(){
        super(Customer)
    }
    @Override
    def index(PaginationCommand paginationCommand){
        def customerList =Customer.createCriteria().list(paginationCommand.params){
            if(params.name){
                like('name' , "%${params.name}%")
            }
        }
        respond JSONFormat.respond(customerList)
    }


}
