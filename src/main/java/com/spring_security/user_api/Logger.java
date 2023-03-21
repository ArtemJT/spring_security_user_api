package com.spring_security.user_api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Logger {

    public static void logMessage(String message) {
        log.info(message);
    }

    public static void logException(String exceptionMsg) {
        log.error("An error occurred: {{}}", exceptionMsg);
    }

    public static void logInvokedMethod() {
        log.info("Calling a method: [{}.{}]",
                Thread.currentThread().getStackTrace()[2].getClassName(),
                Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    public static void logInvokedMethod(String userName) {
        log.info("User [{}] calling a method: [{}.{}]",
                userName,
                Thread.currentThread().getStackTrace()[2].getClassName(),
                Thread.currentThread().getStackTrace()[2].getMethodName());
    }
}
