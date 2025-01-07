package com.example.demo.notificationSetting.application.service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebhookCommonService {

    public String getOperatingSystemInfo() {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        return osName + " " + osVersion;
    }

    public String getJavaRuntimeVersion() {
        return System.getProperty("java.version");
    }

    public double getCpuUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        int processors = osBean.getAvailableProcessors();
        double systemLoadAverage = osBean.getSystemLoadAverage();
        return (systemLoadAverage / processors) * 100;
    }

    public String getMemoryUsage() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();

        long used = memoryUsage.getUsed() / (1024 * 1024);
        long max = memoryUsage.getMax() / (1024 * 1024);

        return String.format("%dMB / %dMB (%.2f%%)", used, max, ((double) used / max) * 100);
    }
}
