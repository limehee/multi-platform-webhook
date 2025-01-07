package com.example.demo.notificationSetting.adapter.out.webhook;

import static com.slack.api.model.block.Blocks.actions;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;

import com.example.demo.notificationSetting.application.service.WebhookCommonService;
import com.example.demo.notificationSetting.config.NotificationConfigProperties;
import com.example.demo.notificationSetting.domain.AlertType;
import com.example.demo.notificationSetting.domain.GeneralAlertType;
import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SlackWebhookClient extends AbstractWebhookClient {

    private final Slack slack;
    private final NotificationConfigProperties.CommonProperties commonProperties;
    private final Environment environment;
    private final WebhookCommonService webhookCommonService;

    public SlackWebhookClient(
        NotificationConfigProperties notificationConfigProperties,
        Environment environment,
        WebhookCommonService webhookCommonService
    ) {
        this.slack = Slack.getInstance();
        this.commonProperties = notificationConfigProperties.getCommon();
        this.environment = environment;
        this.webhookCommonService = webhookCommonService;
    }

    public CompletableFuture<Boolean> sendMessage(String webhookUrl, AlertType alertType,
        HttpServletRequest request, Object additionalData) {
        List<LayoutBlock> blocks = createBlocks(alertType, request, additionalData);

        return CompletableFuture.supplyAsync(() -> {
            Payload payload = Payload.builder()
                .blocks(Collections.singletonList(blocks.getFirst()))
                .attachments(Collections.singletonList(
                    Attachment.builder()
                        .color(commonProperties.getColor())
                        .blocks(blocks.subList(1, blocks.size()))
                        .build()
                ))
                .build();

            try {
                WebhookResponse response = slack.send(webhookUrl, payload);
                if (response.getCode() == HttpStatus.OK.value()) {
                    return true;
                } else {
                    log.error("Slack notification failed: {}", response.getMessage());
                    return false;
                }
            } catch (IOException e) {
                log.error("Failed to send Slack message: {}", e.getMessage(), e);
                return false;
            }
        });
    }

    public List<LayoutBlock> createBlocks(AlertType alertType, HttpServletRequest request, Object additionalData) {
        switch (alertType) {
            case GeneralAlertType generalAlertType -> {
                return createGeneralAlertBlocks(generalAlertType, request, additionalData);
            }
            case null, default -> {
                log.error("Unknown alert type: {}", alertType);
                return Collections.emptyList();
            }
        }
    }

    private List<LayoutBlock> createGeneralAlertBlocks(GeneralAlertType alertType, HttpServletRequest request,
        Object additionalData) {
        switch (alertType) {
            case SERVER_START:
                return createServerStartBlocks();
            default:
                log.error("Unknown general alert type: {}", alertType);
        }
        return Collections.emptyList();
    }

    private List<LayoutBlock> createServerStartBlocks() {
        String osInfo = webhookCommonService.getOperatingSystemInfo();
        String jdkVersion = webhookCommonService.getJavaRuntimeVersion();
        double cpuUsage = webhookCommonService.getCpuUsage();
        String memoryUsage = webhookCommonService.getMemoryUsage();

        return Arrays.asList(
            section(s -> s.text(markdownText(":battery: *Server Started*"))),
            section(s -> s.fields(Arrays.asList(
                markdownText("*Environment:* \n" + environment.getProperty("spring.profiles.active")),
                markdownText("*OS:* \n" + osInfo),
                markdownText("*JDK Version:* \n" + jdkVersion),
                markdownText("*CPU Usage:* \n" + String.format("%.2f%%", cpuUsage)),
                markdownText("*Memory Usage:* \n" + memoryUsage)
            ))),
            actions(a -> a.elements(asElements(
                button(b -> b.text(plainText(pt -> pt.emoji(true).text("Web")))
                    .url(commonProperties.getWebUrl())
                    .value("click_web")),
                button(b -> b.text(plainText(pt -> pt.emoji(true).text("API Docs")))
                    .url(commonProperties.getApiUrl())
                    .value("click_apiDocs"))
            )))
        );
    }
}
