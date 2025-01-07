package com.example.demo.notificationSetting.application.port.out;

import com.example.demo.notificationSetting.domain.AlertType;
import com.example.demo.notificationSetting.domain.NotificationSetting;
import java.util.List;
import java.util.Optional;

public interface RetrieveNotificationSettingPort {

    List<NotificationSetting> findAll();

    Optional<NotificationSetting> findByAlertType(AlertType alertType);
}
