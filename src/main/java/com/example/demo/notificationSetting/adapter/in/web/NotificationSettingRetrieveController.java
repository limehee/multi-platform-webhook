package com.example.demo.notificationSetting.adapter.in.web;

import com.example.demo.notificationSetting.application.dto.response.NotificationSettingResponseDto;
import com.example.demo.notificationSetting.application.port.in.RetrieveNotificationSettingUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification-settings")
@RequiredArgsConstructor
public class NotificationSettingRetrieveController {

    private final RetrieveNotificationSettingUseCase retrieveNotificationSettingUseCase;

    @GetMapping("")
    public ResponseEntity<List<NotificationSettingResponseDto>> getNotificationSettings() {
        List<NotificationSettingResponseDto> notificationSettings = retrieveNotificationSettingUseCase.retrieveNotificationSettings();
        return ResponseEntity.ok(notificationSettings);
    }
}
