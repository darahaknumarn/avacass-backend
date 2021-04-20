package hanuman.team

import grails.gorm.transactions.Transactional
import groovy.time.TimeCategory
import hanuman.email.EmailDTO

@Transactional
class UtilService {
    def mailService

    static private final String defaultConvertTimeZone = "Asia/Shanghai"
    static public final String defaultLivingTimeZone = "Asia/Phnom_Penh"//"Asia/Shanghai"
    static public final String defaultCurrentTimeZone = "Asia/Phnom_Penh"
    static final String dateTimeFormatISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    static final String dateTimeLocal = "yyyy-MM-dd HH:mm"
    static final String dateLocal = "yyyy-MM-dd"
    static final String dateTime = "yyyy-mm-dd'T'hh:mm:ss'Z'"
    static final String defaultGetDate = "yyyy-MM-dd'T'HH:mm:ss"
    static final String defaultSetDate = "yyyy-MM-dd'T'HH:mm:ss'Z'"




    //TODO
    static Date fromDate(String fromDate) {
        return new Date().parse(dateLocal, fromDate)
    }

    static Date toDates(String toDate) {
        return use(TimeCategory) {
            new Date().parse(dateLocal, toDate) + 23.hour + 59.minutes + 59.seconds
        }
    }

    void sendHTMLMail(EmailDTO emailDTO){
        mailService.sendMail {
            to"${emailDTO.sendTo}"
            subject(emailDTO.subject)
            html(emailDTO.htmlContent)
        }
    }
}
