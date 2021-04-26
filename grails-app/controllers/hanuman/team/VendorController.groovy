package hanuman.team

import hanuman.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController

class VendorController extends SimpleGenericRestfulController<Vendor>{

    VendorController() {
        super(Vendor)
    }

}
