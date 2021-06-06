package hanuman.team

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/api/orders"(resources: "Orders")
        "/api/customer"(resources: "Customer")
        "/api/category"(resources: "Category")
        "/api/product"(resources:"Product" )
        "/api/vendor"(resources:"Vendor")
        "/api/deliveryAddress"(resources:"DeliveryAddress" )


    }
}
