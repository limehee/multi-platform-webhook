package com.example.demo.notificationSetting.adapter.out.persistence;

import com.example.demo.notificationSetting.application.port.out.RetrieveNotificationSettingPort;
import com.example.demo.notificationSetting.application.port.out.UpdateNotificationSettingPort;
import com.example.demo.notificationSetting.domain.AlertType;
import com.example.demo.notificationSetting.domain.NotificationSetting;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSettingPersistenceAdapter implements
    RetrieveNotificationSettingPort,
    UpdateNotificationSettingPort {

    private final NotificationSettingRepository repository;

    @Override
    public List<NotificationSetting> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<NotificationSetting> findByAlertType(AlertType alertType) {
        return repository.findByAlertType(alertType);
    }

    @Override
    public NotificationSetting save(NotificationSetting setting) {
        return repository.save(setting);
    }
}
