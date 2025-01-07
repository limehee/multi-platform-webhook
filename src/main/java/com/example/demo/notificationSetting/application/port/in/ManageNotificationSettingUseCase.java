package com.example.demo.notificationSetting.application.port.in;

import com.example.demo.notificationSetting.domain.AlertType;
import com.example.demo.notificationSetting.domain.NotificationSetting;

public interface ManageNotificationSettingUseCase {

    void toggleNotificationSetting(String alertTypeName, boolean enabled);

    NotificationSetting getOrCreateDefaultSetting(AlertType alertType);
}
