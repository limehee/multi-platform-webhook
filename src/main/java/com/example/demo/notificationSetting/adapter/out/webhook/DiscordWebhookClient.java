package com.example.demo.notificationSetting.adapter.out.webhook;

import com.example.demo.notificationSetting.application.service.WebhookCommonService;
import com.example.demo.notificationSetting.config.NotificationConfigProperties;
import com.example.demo.notificationSetting.domain.AlertType;
import com.example.demo.notificationSetting.domain.GeneralAlertType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DiscordWebhookClient extends AbstractWebhookClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final NotificationConfigProperties.CommonProperties commonProperties;
    private final Environment environment;
    private final WebhookCommonService webhookCommonService;

    public DiscordWebhookClient(
        NotificationConfigProperties notificationConfigProperties,
        ObjectMapper objectMapper,
        Environment environment,
        WebhookCommonService webhookCommonService
    ) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
        this.commonProperties = notificationConfigProperties.getCommon();
        this.environment = environment;
        this.webhookCommonService = webhookCommonService;
    }

    public CompletableFuture<Boolean> sendMessage(String webhookUrl, AlertType alertType,
        HttpServletRequest request, Object additionalData) {
        Map<String, Object> payload = createPayload(alertType, request, additionalData);

        return CompletableFuture.supplyAsync(() -> {
            try {
                String jsonPayload = objectMapper.writeValueAsString(payload);

                HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(webhookUrl))
                    .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

                HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                log.info("Discord notification response header: {}", response.headers());
                log.info("Discord notification response code: {}", response.statusCode());
                log.info("Discord notification response: {}", response.body());

                if (response.statusCode() == HttpStatus.NO_CONTENT.value()) {
                    return true;
                } else {
                    log.error("Discord notification failed: {}", response.body());
                    return false;
                }
            } catch (IOException | InterruptedException e) {
                log.error("Failed to send Discord message: {}", e.getMessage(), e);
                return false;
            }
        });
    }

    public Map<String, Object> createPayload(AlertType alertType, HttpServletRequest request, Object additionalData) {
        List<Map<String, Object>> embeds = createEmbeds(alertType, request, additionalData);

        Map<String, Object> payload = new HashMap<>();
        payload.put("embeds", embeds);

        return payload;
    }

    public List<Map<String, Object>> createEmbeds(AlertType alertType, HttpServletRequest request,
        Object additionalData) {
        switch (alertType) {
            case GeneralAlertType generalAlertType -> {
                return createGeneralAlertEmbeds(generalAlertType, request, additionalData);
            }
            case null, default -> {
                log.error("Unknown alert type: {}", alertType);
                return Collections.emptyList();
            }
        }
    }

    private List<Map<String, Object>> createGeneralAlertEmbeds(GeneralAlertType alertType, HttpServletRequest request,
        Object additionalData) {
        switch (alertType) {
            case SERVER_START:
                return createServerStartEmbeds();
            default:
                log.error("Unknown general alert type: {}", alertType);
        }
        return Collections.emptyList();
    }

    private List<Map<String, Object>> createServerStartEmbeds() {
        String osInfo = webhookCommonService.getOperatingSystemInfo();
        String jdkVersion = webhookCommonService.getJavaRuntimeVersion();
        double cpuUsage = webhookCommonService.getCpuUsage();
        String memoryUsage = webhookCommonService.getMemoryUsage();

        Map<String, Object> embed = new HashMap<>();
        embed.put("title", ":battery: Server Started");
        embed.put("color", commonProperties.getColorAsInt());
        embed.put("fields", Arrays.asList(
            createField("Environment", environment.getProperty("spring.profiles.active", "default"), true),
            createField("OS", osInfo, true),
            createField("JDK Version", jdkVersion, true),
            createField("CPU Usage", String.format("%.2f%%", cpuUsage), true),
            createField("Memory Usage", memoryUsage, true)
        ));

        embed.put("description",
            "[Web](" + commonProperties.getWebUrl() + ") | [API Docs](" + commonProperties.getApiUrl() + ")");

        return Collections.singletonList(embed);
    }

    private Map<String, Object> createField(String name, String value, boolean inline) {
        Map<String, Object> field = new HashMap<>();
        field.put("name", name);
        field.put("value", value);
        field.put("inline", inline);
        return field;
    }
}
