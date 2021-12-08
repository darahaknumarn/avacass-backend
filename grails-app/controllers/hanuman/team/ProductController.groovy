package hanuman.team

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.PaginationCommand
import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import static org.springframework.http.HttpStatus.OK

class ProductController extends SimpleGenericRestfulController<Product> implements GrailsConfigurationAware{
    def productService
    ProductController() {
        super(Product)
    }
    String xlsxMimeType
    String encoding

    @Override
    def index(PaginationCommand paginationCommand){

//        set unlimit page in case export excel
        if (params.export){
            paginationCommand.setMax(100000) // = 100000;
        }

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
            if (params.barcode) {
                eq("barcode", params.barcode)
            }
            if (params.status) {
                eq("status", params.boolean("status"))
            }
            if(params.reOrder){
                gtProperty('minimumStockQty' , 'stockBalance')
            }

            if (params.search) {
                or {
                    like("title", "%${params.name}%")
                    like("barcode", "%${params.barcode}%")
                }
            }
        }
//         in case user export excel file
        if(params.export){

            response.status = OK.value()
            response.setHeader "Content-disposition", "attachment; filename=${productService.EXCEL_FILENAME}"
            response.contentType = "${xlsxMimeType};charset=${encoding}"
            OutputStream outs = response.outputStream
            productService.exportExcel(outs, list)
            outs.flush()
            outs.close()

        }else{

            respond JSONFormat.respond(list)
        }
    }

    @Override
    void setConfiguration(Config co) {
        xlsxMimeType = co.getProperty('grails.mime.types.xlsxMimeType',
                String,
                'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
        encoding = co.getProperty('grails.converters.encoding', String, 'UTF-8')
    }

    @Override
    def beforeSave(Product product){
        return productService.mapImageToProduct(product)
    }

    @Override
    def afterSaved(Product product) {
        // adding stock balance
        new StockBalance(
                productId: product.id,
                stockBalance: product.availableStockQty
        ).save(flush: true)
        return product
    }

    @Override
    def afterUpdated(Product product){
        return productService.mapImageToProduct(product)
    }

    @Override
    def delete( ){ // soft delete
        def product = Product.get(params.id)
        if (product.status.equals(true))
            product.status = false
        else
            product.status = true

        product.save(flush:true )
        respond JSONFormat.respondSingleObject(product)
    }

}
