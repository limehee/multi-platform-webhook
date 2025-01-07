package com.example.demo.notificationSetting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    @Bean
    public NotificationConfigProperties notificationConfigProperties() {
        return new NotificationConfigProperties();
    }
}
