package hanuman.team

import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController

class ProductController extends SimpleGenericRestfulController<Product> {
    def productService
    ProductController() {
        super(Product)
    }
    @Override
    def index(PaginationCommand paginationCommand){
        def list = Product.createCriteria().list(paginationCommand.params){
            if(params.categoryId){
                eq("categoryId" , params.categoryId as Long)
            }
            if(params.title){
                like("title" , "%${params.title}%")
            }
        }



        respond JSONFormat.respond(list)
    }
    @Override
    def show() {

        def data =Product.get(params.id).properties
        respond JSONFormat.respondSingleObject(data)
    }

    @Override
    def beforeSave(Product product){


        return productService.mapImageToProduct(product)
    }

    @Override
    def afterUpdated(Product product){
        return productService.mapImageToProduct(product)
    }


}
