package hanuman.team

import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController

class CategoryController extends SimpleGenericRestfulController<Category>{

    CategoryController() {
        super(Category)
    }
    @Override
    def index(PaginationCommand paginationCommand){
        def list = Category.createCriteria().list(paginationCommand.params){
            if(params.categoryName){
                like("categoryName" , "%${params.categoryName}%")
            }
        }
        respond JSONFormat.respond(list)
    }
}
