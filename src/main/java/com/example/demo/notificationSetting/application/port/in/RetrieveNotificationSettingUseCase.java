package com.example.demo.notificationSetting.application.port.in;

import com.example.demo.notificationSetting.application.dto.response.NotificationSettingResponseDto;
import java.util.List;

public interface RetrieveNotificationSettingUseCase {

    List<NotificationSettingResponseDto> retrieveNotificationSettings();
}
