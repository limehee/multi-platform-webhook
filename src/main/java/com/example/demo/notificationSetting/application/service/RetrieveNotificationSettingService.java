package com.example.demo.notificationSetting.application.service;

import com.example.demo.notificationSetting.application.dto.mapper.NotificationSettingDtoMapper;
import com.example.demo.notificationSetting.application.dto.response.NotificationSettingResponseDto;
import com.example.demo.notificationSetting.application.port.in.RetrieveNotificationSettingUseCase;
import com.example.demo.notificationSetting.application.port.out.RetrieveNotificationSettingPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RetrieveNotificationSettingService implements RetrieveNotificationSettingUseCase {

    private final RetrieveNotificationSettingPort retrieveNotificationSettingPort;
    private final NotificationSettingDtoMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public List<NotificationSettingResponseDto> retrieveNotificationSettings() {
        return retrieveNotificationSettingPort.findAll().stream()
            .map(mapper::toDto)
            .toList();
    }
}
