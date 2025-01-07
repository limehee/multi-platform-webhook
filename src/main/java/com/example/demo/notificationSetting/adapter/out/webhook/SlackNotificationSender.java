package com.example.demo.notificationSetting.adapter.out.webhook;

import com.example.demo.notificationSetting.application.event.NotificationEvent;
import com.example.demo.notificationSetting.application.port.out.NotificationSender;
import com.example.demo.notificationSetting.domain.PlatformType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlackNotificationSender implements NotificationSender {

    private final SlackWebhookClient slackWebhookClient;

    @Override
    public String getPlatformName() {
        return PlatformType.SLACK.getName();
    }

    @Override
    public void sendNotification(NotificationEvent event, String webhookUrl) {
        slackWebhookClient.sendMessage(webhookUrl, event.getAlertType(), event.getRequest(),
            event.getAdditionalData());
    }
}
