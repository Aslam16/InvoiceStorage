package com.invoice.Storage.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoice.Storage.kafka.configurations.Configs;
import com.invoice.Storage.models.InvoiceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InvoiceProducer {
    private final KafkaTemplate<String, String> producerTemplate;
    private final Configs configs;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    public InvoiceProducer(KafkaTemplate<String, String> producerTemplate, Configs configs) {
        this.producerTemplate = producerTemplate;
        this.configs = configs;
    }

    public void sendInvoiceToKafka(InvoiceView invoiceView) throws JsonProcessingException {
        String jsonData = objectMapper.writeValueAsString(invoiceView);
        producerTemplate.send(configs.getInvoiceTopic(), invoiceView.getInvoiceNumber(), jsonData);
    }
}
