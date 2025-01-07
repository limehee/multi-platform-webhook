package com.example.demo.notificationSetting.application.service;

import com.example.demo.notificationSetting.application.port.in.ManageNotificationSettingUseCase;
import com.example.demo.notificationSetting.application.port.out.RetrieveNotificationSettingPort;
import com.example.demo.notificationSetting.application.port.out.UpdateNotificationSettingPort;
import com.example.demo.notificationSetting.domain.AlertType;
import com.example.demo.notificationSetting.domain.AlertTypeResolver;
import com.example.demo.notificationSetting.domain.NotificationSetting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManageNotificationSettingService implements ManageNotificationSettingUseCase {

    private final AlertTypeResolver alertTypeResolver;
    private final RetrieveNotificationSettingPort retrieveNotificationSettingPort;
    private final UpdateNotificationSettingPort updateNotificationSettingPort;

    @Transactional
    @Override
    public void toggleNotificationSetting(String alertTypeName, boolean enabled) {
        AlertType alertType = alertTypeResolver.resolve(alertTypeName);
        NotificationSetting setting = getOrCreateDefaultSetting(alertType);
        setting.updateEnabled(enabled);
        updateNotificationSettingPort.save(setting);
    }

    @Transactional
    public NotificationSetting getOrCreateDefaultSetting(AlertType alertType) {
        return retrieveNotificationSettingPort.findByAlertType(alertType)
            .orElseGet(() -> createAndSaveDefaultSetting(alertType));
    }

    private NotificationSetting createAndSaveDefaultSetting(AlertType alertType) {
        NotificationSetting defaultSetting = NotificationSetting.createDefault(alertType);
        return updateNotificationSettingPort.save(defaultSetting);
    }
}
