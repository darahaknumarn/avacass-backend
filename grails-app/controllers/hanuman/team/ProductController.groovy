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
            if (params.isTopSale) {
                eq("isTopsSale", Boolean.parseBoolean(params.isTopSale))
            }
            if (params.isRecommend) {
                eq("isRecommend", Boolean.parseBoolean(params.isRecommend))
            }

            if (params.name) {
                like("title", "%${params.name}%")
            }
            if (params.categoryId) {
                eq("categoryId", params.long("categoryId"))
            }
            if (params.vendorId) {
                eq("vendorId", params.long("vendorId"))
            }
        }

        respond JSONFormat.respond(list)
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
