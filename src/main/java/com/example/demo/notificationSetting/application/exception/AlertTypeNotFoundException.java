package com.example.demo.notificationSetting.application.exception;

public class AlertTypeNotFoundException extends RuntimeException {

    public AlertTypeNotFoundException(String alertTypeName) {
        super("Unknown alert type: " + alertTypeName);
    }
}
