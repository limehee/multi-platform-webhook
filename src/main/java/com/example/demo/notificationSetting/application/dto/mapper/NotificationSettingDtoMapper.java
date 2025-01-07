package com.example.demo.notificationSetting.application.dto.mapper;

import com.example.demo.notificationSetting.application.dto.response.NotificationSettingResponseDto;
import com.example.demo.notificationSetting.domain.NotificationSetting;
import org.springframework.stereotype.Component;

@Component
public class NotificationSettingDtoMapper {

    public NotificationSettingResponseDto toDto(NotificationSetting setting) {
        return NotificationSettingResponseDto.builder()
            .alertType(setting.getAlertType().getTitle())
            .enabled(setting.isEnabled())
            .build();
    }
}
