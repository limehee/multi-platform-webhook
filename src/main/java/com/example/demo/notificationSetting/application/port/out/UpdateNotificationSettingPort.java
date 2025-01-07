package com.example.demo.notificationSetting.application.port.out;

import com.example.demo.notificationSetting.domain.NotificationSetting;

public interface UpdateNotificationSettingPort {

    NotificationSetting save(NotificationSetting setting);
}
