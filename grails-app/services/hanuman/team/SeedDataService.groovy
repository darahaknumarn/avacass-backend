package hanuman.team

import grails.gorm.transactions.Transactional
import hanuman.security.Requestmap

@Transactional
class SeedDataService {
    def springSecurityService
    def up() {
        addReqeustmap()
    }

    def addReqeustmap(){
        new Requestmap(url: "/api/orderActivity/**",configAttribute: "isAuthenticated()").save()
        new Requestmap(url: "/api/orderActivity/comment/**", configAttribute: "isAuthenticated()").save()
        new Requestmap(url: "/api/purchase/**", configAttribute: "isAuthenticated()").save()
        new Requestmap(url: "/api/uom/**", configAttribute: "isAuthenticated()").save()
        new Requestmap(url: "/api/orders/packed/**", configAttribute: "isAuthenticated()").save()


        springSecurityService.clearCachedRequestmaps()
    }
}
