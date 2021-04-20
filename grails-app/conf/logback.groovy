import ch.qos.logback.classic.AsyncAppender
import ch.qos.logback.classic.db.DBAppender
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.db.DriverManagerConnectionSource
import ch.qos.logback.core.util.FileSize
import grails.util.BuildSettings
import grails.util.Environment
import org.springframework.boot.logging.logback.ColorConverter
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter

import java.nio.charset.Charset

conversionRule 'clr', ColorConverter
conversionRule 'wex', WhitespaceThrowableProxyConverter

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName('UTF-8')

        pattern =
                '%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} ' + // Date
                        '%clr(%5p) ' + // Log level
                        //'%clr(---){faint} %clr([%15.15t]){faint} ' + // Thread
                        '%clr(%-40.40logger{30}){cyan} %clr(:){faint} ' + // Logger
                        '%m%n%wex' // Message
    }
}

def targetDir = BuildSettings.TARGET_DIR
if (Environment.isDevelopmentMode() && targetDir != null) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}

def dateFile = new Date().format('yyyy-MM-dd') // current date of file
def LOG_PATH = "logs" // path to write log
def LOG_ARCHIVE = "${LOG_PATH}/archive" // path to append to zip

// write logger file for error file
appender("ROLLING-ERROR", RollingFileAppender) {
    file = "${LOG_PATH}/errorFile.${dateFile}.log"
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS a} %-6p [%t] %c :%L ====== %m%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_ARCHIVE}/errorFile.%d{yyyy-MM-dd-HH}.zip" //property defines a file name pattern for archived log files
        maxHistory = 30 // property sets the maximum number of archive files to keep, before deleting.
        totalSizeCap = FileSize.valueOf("100MB") //Oldest archives are deleted asynchronously when the total size cap is exceeded
    }
    filter(ThresholdFilter){
        level = ERROR // set log level
    }
}

// write logger file for all or info file
appender("ROLLING-INFO", RollingFileAppender) {
    file = "${LOG_PATH}/infoFile.${dateFile}.log"
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS a} %-6p [%t] %c :%L ====== %m%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_ARCHIVE}/infoFile.%d{yyyy-MM-dd-HH}.zip" //property defines a file name pattern for archived log files
        maxHistory = 30 // each file should be at most 100MB, keep 30 days worth of history, before deleting.
        totalSizeCap = FileSize.valueOf("100MB") //Oldest archives are deleted asynchronously when the total size cap is exceeded
    }
    filter(ThresholdFilter){
        level = INFO // set log level
    }
}

//DB appender write to database
//appender("DB-INFO", DBAppender){
//    connectionSource(DriverManagerConnectionSource){
//        driverClass = "com.mysql.jdbc.Driver"
//        url = "jdbc:mysql://172.30.30.23/dev_corebackend"
//        user = "developer"
//        password = "P@ssw0rd"
//    }
//}
//AsyncAppender It acts solely as an event dispatcher and must therefore reference another appender in order to do anything useful
// appender ref to file name rolling error
appender("ERROR", AsyncAppender) {
    appenderRef("ROLLING-ERROR")
}
// appender ref to file name rolling info
appender("INFO", AsyncAppender) {
    appenderRef("ROLLING-INFO")
}
// appender ref to file name db info
//appender("DB", AsyncAppender) {
//    appenderRef("DB-INFO")
//}

//logger("corebackend", INFO, ["STDOUT"], true)
//logger("org.springframework.security", DEBUG, ['STDOUT'], false)
//logger("grails.plugin.springsecurity", DEBUG, ['STDOUT'], false)
root(INFO, ['STDOUT', 'ERROR', 'INFO'])
