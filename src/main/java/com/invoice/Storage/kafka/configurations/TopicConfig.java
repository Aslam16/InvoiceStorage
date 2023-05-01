package com.invoice.Storage.kafka.configurations;

import lombok.Getter;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Getter
public class TopicConfig {


    private final Configs configs;

    @Autowired
    public TopicConfig(Configs configs) {
        this.configs = configs;
    }
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> props = new HashMap<>();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, configs.getKafkaServerURL());
        return new KafkaAdmin(props);
    }

    @Bean
    public NewTopic createInvoiceTopic() {
        return new NewTopic(configs.getInvoiceTopic(), 1, (short) 1);
    }
}
