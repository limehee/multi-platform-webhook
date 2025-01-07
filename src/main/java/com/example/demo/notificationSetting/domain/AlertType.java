package com.example.demo.notificationSetting.domain;

public interface AlertType {

    String getTitle();

    String getDefaultMessage();

    AlertCategory getCategory();
}
