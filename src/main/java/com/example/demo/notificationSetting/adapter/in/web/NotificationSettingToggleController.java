package com.example.demo.notificationSetting.adapter.in.web;

import com.example.demo.notificationSetting.application.dto.request.NotificationSettingToggleRequestDto;
import com.example.demo.notificationSetting.application.port.in.ManageNotificationSettingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification-settings")
@RequiredArgsConstructor
public class NotificationSettingToggleController {

    private final ManageNotificationSettingUseCase manageNotificationSettingUseCase;

    @PutMapping("")
    public ResponseEntity<Void> toggleNotificationSetting(
        @RequestBody NotificationSettingToggleRequestDto requestDto
    ) {
        manageNotificationSettingUseCase.toggleNotificationSetting(requestDto.getAlertType(), requestDto.isEnabled());
        return ResponseEntity.ok().build();
    }
}
