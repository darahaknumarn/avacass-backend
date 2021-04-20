package spring

import hanuman.odp.CoordinateValidatorService
import hanuman.security.CustomLogoutHandlerService
import hanuman.security.CustomTokenStorageService
import hanuman.security.SecUserPasswordEncoderListener

// Place your Spring DSL code here
beans = {
    tokenStorageService(CustomTokenStorageService)

    coordinateValidator(CoordinateValidatorService)

    secUserPasswordEncoderListener(SecUserPasswordEncoderListener)

    customLogoutHandler(CustomLogoutHandlerService){

    }

}
