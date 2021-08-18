package hanuman.team

class BootStrap {

    def seedDataService
    def init = { servletContext ->
        seedDataService.up()
    }
    def destroy = {
    }
}
