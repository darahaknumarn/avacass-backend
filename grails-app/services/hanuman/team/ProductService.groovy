package hanuman.team

import grails.gorm.transactions.Transactional

@Transactional
class ProductService {

    Product mapImageToProduct(Product product){
        def productImage = product.productImage.find {
            it.isBaseImage == true
        }
        product.imageUrl =  productImage?.thumbnailUrl
        return product
    }
}
