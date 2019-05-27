package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppConfig {
    @Value("${info.app.name}")
    private String appName;

    @Value("${info.app.description}")
    private String appDescription;
}
