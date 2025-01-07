package com.example.demo.notificationSetting.application.event;

import com.example.demo.notificationSetting.domain.AlertType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NotificationEvent extends ApplicationEvent {

    private final AlertType alertType;
    private final HttpServletRequest request;
    private final Object additionalData;

    public NotificationEvent(Object source, AlertType alertType, HttpServletRequest request, Object additionalData) {
        super(source);
        this.alertType = alertType;
        this.request = request;
        this.additionalData = additionalData;
    }
}
