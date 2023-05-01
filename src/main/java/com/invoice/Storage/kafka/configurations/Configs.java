package com.invoice.Storage.kafka.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Scope("singleton")
@Getter
public class Configs {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServerURL;
    @Value("${spring.kafka.topic.name}")
    private String invoiceTopic;
}
