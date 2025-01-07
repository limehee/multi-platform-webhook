package com.example.demo.notificationSetting.adapter.out.webhook;

import com.example.demo.notificationSetting.application.port.out.WebhookClient;
import com.example.demo.notificationSetting.domain.AlertType;
import jakarta.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractWebhookClient implements WebhookClient {

    @Override
    public abstract CompletableFuture<Boolean> sendMessage(String webhookUrl, AlertType alertType,
        HttpServletRequest request,
        Object additionalData);
}
