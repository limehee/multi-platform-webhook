package com.example.demo.notificationSetting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GeneralAlertType implements AlertType {

    SERVER_START("서버 시작", "Server has been started.", AlertCategory.GENERAL);

    private final String title;
    private final String defaultMessage;
    private final AlertCategory category;
}
