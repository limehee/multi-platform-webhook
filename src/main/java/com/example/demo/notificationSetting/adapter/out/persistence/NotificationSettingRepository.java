package com.example.demo.notificationSetting.adapter.out.persistence;

import com.example.demo.notificationSetting.domain.AlertType;
import com.example.demo.notificationSetting.domain.NotificationSetting;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {

    Optional<NotificationSetting> findByAlertType(AlertType alertType);
}
