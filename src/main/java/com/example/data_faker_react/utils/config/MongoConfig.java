package com.example.data_faker_react.utils.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.mongodb")
@Data
public class MongoConfig {
    private String host;
    private String port;
}
