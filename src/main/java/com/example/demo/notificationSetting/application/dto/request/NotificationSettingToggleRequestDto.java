package com.example.demo.notificationSetting.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationSettingToggleRequestDto {

    private String alertType;
    private boolean enabled;
}
