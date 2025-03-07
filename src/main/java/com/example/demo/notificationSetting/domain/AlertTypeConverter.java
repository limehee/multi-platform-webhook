package com.example.demo.notificationSetting.domain;

import com.example.demo.notificationSetting.application.exception.AlertTypeNotFoundException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

@Converter(autoApply = true)
public class AlertTypeConverter implements AttributeConverter<AlertType, String> {

    private static final Map<String, AlertType> CACHE = new HashMap<>();

    static {
        for (GeneralAlertType type : GeneralAlertType.values()) {
            CACHE.put(type.getTitle(), type);
        }
    }

    @Override
    public String convertToDatabaseColumn(AlertType alertType) {
        if (alertType == null) {
            return null;
        }
        return alertType.getTitle();
    }

    @Override
    public AlertType convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        AlertType alertType = CACHE.get(dbData);
        if (alertType == null) {
            throw new AlertTypeNotFoundException(dbData);
        }
        return alertType;
    }
}
