package com.example.demo.notificationSetting.application.port.out;

import com.example.demo.notificationSetting.domain.AlertType;
import jakarta.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

public interface WebhookClient {

    CompletableFuture<Boolean> sendMessage(String webhookUrl, AlertType alertType, HttpServletRequest request,
        Object additionalData);
}
