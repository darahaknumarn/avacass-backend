package hanuman.team

import builders.dsl.spreadsheet.builder.poi.PoiSpreadsheetBuilder
import grails.gorm.transactions.Transactional

import java.lang.reflect.Array

@Transactional
class ProductService {

    public static final String SHEET_NAME = "Product"

    public static final ArrayList<String> HEADER_NAME = ["Barcode", "Product Name", "Category Name", "Vendor Name", "Price", "Stock on Hand", "Minimum Stock Qty", "Re Order Point", "Bin Location"]
    public static final String EXCEL_FILE_SUFIX = ".xlsx"
    public static final String EXCEL_FILE_PREFIX = "products"
    public static final String EXCEL_FILENAME = EXCEL_FILE_PREFIX + EXCEL_FILE_SUFIX

    void exportExcel(OutputStream outs, List<Product> products) {
        File file = File.createTempFile(EXCEL_FILE_PREFIX, EXCEL_FILE_SUFIX)
        PoiSpreadsheetBuilder.create(outs).build {
           // apply BookExcelStylesheet
            sheet(SHEET_NAME) { s ->
                row{
                    cell('Product list')
                }
                row {
                    cell('total record: ')
                    cell(products.size())
                }
                row {
                    HEADER_NAME.each { header ->
                        cell {
                            value header
//                            style BookExcelStylesheet.STYLE_HEADER
                        }
                    }
                }
                products.each { book ->
                    row {
                        cell(book.barcode)
                        cell(book.title)
                        cell(book.categoryName)
                        cell(book.vendorName)
                        cell(book.price)
                        cell(book.stockBalance)
                        cell(book.minimumStockQty)
                        cell(book.reOrderPoint)
                        cell(book.binLocationCode)
                    }
                }
            }
        }
        file
    }
    Product mapImageToProduct(Product product){
        def productImage = product.productImage.find {
            it.isBaseImage == true
        }
        product.imageUrl =  productImage?.thumbnailUrl
        return product
    }


}
