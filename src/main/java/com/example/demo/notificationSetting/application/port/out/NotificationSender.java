package com.example.demo.notificationSetting.application.port.out;

import com.example.demo.notificationSetting.application.event.NotificationEvent;

public interface NotificationSender {

    String getPlatformName();

    void sendNotification(NotificationEvent event, String webhookUrl);
}
