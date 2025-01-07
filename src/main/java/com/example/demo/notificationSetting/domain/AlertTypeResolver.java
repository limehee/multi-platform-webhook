package com.example.demo.notificationSetting.domain;

import com.example.demo.notificationSetting.application.exception.AlertTypeNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AlertTypeResolver {

    public AlertType resolve(String alertTypeName) {
        for (GeneralAlertType type : GeneralAlertType.values()) {
            if (type.getTitle().equals(alertTypeName)) {
                return type;
            }
        }
        throw new AlertTypeNotFoundException(alertTypeName);
    }
}
