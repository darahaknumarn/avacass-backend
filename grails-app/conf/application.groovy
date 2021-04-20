//	stateless chain that allows anonymous access when no token is sent. If however a token is on the request, it will be validated.
String ANONYMOUS_FILTERS = 'anonymousAuthenticationFilter,restTokenValidationFilter,restExceptionTranslationFilter,filterInvocationInterceptor'
// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'hanuman.security.SecUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName ='hanuman.security.SecUserRole'
grails.plugin.springsecurity.authority.className = 'hanuman.security.security.Role'
grails.plugin.springsecurity.requestMap.className = 'hanuman.security.Requestmap'
grails.plugin.springsecurity.securityConfigType = 'Requestmap'

//JSON credential extraction
grails.plugin.springsecurity.rest.login.useJsonCredentials = true
grails.plugin.springsecurity.rest.login.usernamePropertyName = "username"
grails.plugin.springsecurity.rest.login.passwordPropertyName = "password"

grails.plugin.springsecurity.logout.handlerNames = [
        'rememberMeServices','securityContextLogoutHandler', 'customLogoutHandler'
]
grails.plugin.springsecurity.rest.logout.endpointUrl= "/api/logout"

grails.plugin.springsecurity.useSecurityEventListener = true

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**',      filters: 'none'],
        [pattern: '/**/js/**',       filters: 'none'],
        [pattern: '/**/css/**',      filters: 'none'],
        [pattern: '/**/images/**',   filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/**',filters: 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
        [pattern: '/auth/success', filters: ANONYMOUS_FILTERS],
        [pattern: '/oauth/authenticate/google', filters: ANONYMOUS_FILTERS],
        [pattern: '/oauth/authenticate/facebook', filters: ANONYMOUS_FILTERS],
        [pattern: '/oauth/callback/facebook', filters: ANONYMOUS_FILTERS],
        [pattern: '/oauth/callback/google', filters: ANONYMOUS_FILTERS],
        [pattern: '/', filters: ANONYMOUS_FILTERS],
        //Stateless chain
        [pattern: '/api/**', filters: 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter']
]

//-- use for api logout
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        //pomitted the rest for brevity
        [pattern: '/api/logout', access: ['isAuthenticated()']
        ]
]


grails {
    plugin {
        springsecurity {
            rest {
                token {
                    validation {
                        //user bearer token for validation
                        useBearerToken = true
                        enableAnonymousAccess = true
                    }
                    storage {
                        //user Redis to store token
                        useRedis = true
                    }
                }
//				https://staging.trendsoftinnovation.com:8800/oauth/callback/facebook
//				http://localhost:8080/oauth/callback/facebook
                oauth {
                    // Call back url, after selecting your 2 oAuth provider.
                    frontendCallbackUrl = { String tokenValue -> "http://localhost:8080/auth/success?token=${tokenValue}" }
                    google {
                        //Which pac4j client to use; in our case the Google client
                        client = org.pac4j.oauth.client.Google2Client
                        //Google Client_id
                        key = '1061272590386-ivhha5p2jp4bighjv3m1sutm9s0mh5h6.apps.googleusercontent.com' //<6>
                        //Google Client_secret
                        secret = '_xRk2NugvjIZkrbkdeqV6JF_' //<7>
                        //The scope can be from any value of the enum org.pac4j.oauth.client.Google2Client.Google2Scope
                        scope = org.pac4j.oauth.client.Google2Client.Google2Scope.EMAIL_AND_PROFILE //<8>
                        //Default role set to role google
                        defaultRoles = ['ROLE_GOOGLE'] //<9>
                    }
                    facebook {
                        client = org.pac4j.oauth.client.FacebookClient
                        key = '588876421538466'
                        secret = 'bd6f8b963383834a7d57873af20c7e1b'
                        scope = 'public_profile,email'
                        fields = 'id,name,first_name,middle_name,last_name,username'
                        defaultRoles = ['ROLE_FACEBOOK']
                    }
                }
            }
            providerNames = ['anonymousAuthenticationProvider','daoAuthenticationProvider'] // <10>
        }

    }
    mail {
        host = "smtp.gmail.com"
        port = 465
        username = "idemo3197@gmail.com"
        password = "2021-P@ssw0rd"
        props = ["mail.smtp.auth":"true",
                 "mail.smtp.socketFactory.port":"465",
                 "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                 "mail.smtp.socketFactory.fallback":"false"]
    }

}

grails.mail.default.from = "idemo3197@gmail.com"
grails.views.default.codec = "html"
grails.views.gsp.encoding = "UTF-8"
