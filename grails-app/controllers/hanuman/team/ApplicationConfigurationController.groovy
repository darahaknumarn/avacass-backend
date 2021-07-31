package hanuman.team

import grails.converters.JSON
import hanuman.simplegenericrestfulcontroller.generic.JSONFormat
import hanuman.simplegenericrestfulcontroller.generic.StatusCode

class ApplicationConfigurationController {
    def applicationConfigurationService

    def getDeliveryTime() {
        def deliveryTimeValue = applicationConfigurationService.getApplicationConfigurationByNameAndIsActive("DeliveryTime")
        if (!deliveryTimeValue) {
            render JSONFormat.respondSingleObject(null, StatusCode.OK) as JSON
            return
        }

        def deliveryTime = JSON.parse(deliveryTimeValue?.value)
        render JSONFormat.respondSingleObject(deliveryTime, StatusCode.OK) as JSON
    }

    def getDeliveryDestination() {
        def deliveryDestinationValue = applicationConfigurationService.getApplicationConfigurationByNameAndIsActive("DeliveryDestination")
        if (!deliveryDestinationValue) {
            render JSONFormat.respondSingleObject(null, StatusCode.OK) as JSON
            return
        }

        def deliveryDestination = JSON.parse(deliveryDestinationValue?.value)
        render JSONFormat.respondSingleObject(deliveryDestination, StatusCode.OK) as JSON
    }

    def getMinOrderPrice() {
        def min = applicationConfigurationService.getApplicationConfigurationByNameAndIsActive("MinOrderPrice")
        if (!min) {
            render JSONFormat.respondSingleObject(null, StatusCode.OK) as JSON
            return
        }

        def deliveryDestination = JSON.parse(min?.value)
        render JSONFormat.respondSingleObject(deliveryDestination, StatusCode.OK) as JSON
    }
}
