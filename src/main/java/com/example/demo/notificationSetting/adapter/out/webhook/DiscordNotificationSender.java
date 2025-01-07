package com.example.demo.notificationSetting.adapter.out.webhook;

import com.example.demo.notificationSetting.application.event.NotificationEvent;
import com.example.demo.notificationSetting.application.port.out.NotificationSender;
import com.example.demo.notificationSetting.domain.PlatformType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscordNotificationSender implements NotificationSender {

    private final DiscordWebhookClient discordWebhookClient;

    @Override
    public String getPlatformName() {
        return PlatformType.DISCORD.getName();
    }

    @Override
    public void sendNotification(NotificationEvent event, String webhookUrl) {
        discordWebhookClient.sendMessage(webhookUrl, event.getAlertType(), event.getRequest(),
            event.getAdditionalData());
    }
}
