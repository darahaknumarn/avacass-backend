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

        springSecurityService.clearCachedRequestmaps()
    }
}
