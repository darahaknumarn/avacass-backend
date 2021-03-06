package hanuman.team

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/api/orders"(resources: "Orders")
        "/api/orders/packed"(controller: "orders" , action: "packed")
        "/api/customer"(resources: "Customer")
        "/api/category"(resources: "Category")
        "/api/product"(resources:"Product" )
        "/api/vendor"(resources:"Vendor")
        "/api/deliveryAddress"(resources:"DeliveryAddress" )

        // Banner
        "/api/banner"(resources:"Banner" )
        // Address schedule
        "/api/deliverySchedule"(resources: "DeliverySchedule")
        "/api/orderActivity"(resources:"OrderActivity")
        "/api/orderActivity/comment"(controller:"OrderActivity", action: "addComment")

        // Purchase
        "/api/purchase"(resources: "Purchase")

        // stock adjustment
        "/api/stockAdjustment"(resources: "StockAdjustment")

        // UoM
        "/api/uom"(resources: "UoM")

        // Application Configuration
        "/api/applicationConfiguration/getDeliveryDestination"(controller: "ApplicationConfiguration" , action: "getDeliveryDestination")
        "/api/applicationConfiguration/getDeliveryTime"(controller: "ApplicationConfiguration" , action: "getDeliveryTime")
        "/api/applicationConfiguration/minOrderPrice"(controller: "ApplicationConfiguration" , action: "getMinOrderPrice")
        "/api/applicationConfiguration/getDeliveryFee"(controller: "ApplicationConfiguration" , action: "getDeliveryFee")
    }
}
