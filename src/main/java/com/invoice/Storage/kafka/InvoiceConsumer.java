package com.invoice.Storage.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoice.Storage.controller.Controller;
import com.invoice.Storage.models.InvoiceView;
import com.invoice.Storage.service.InvoiceDBService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InvoiceConsumer {
    private final Logger logger = Logger.getLogger(InvoiceConsumer.class.getName());

    private final InvoiceDBService dbService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public InvoiceConsumer(InvoiceDBService dbService) {
        this.dbService = dbService;
    }

    /**
     * For every message consumed from kafka, we call the following method which creates the db record
     * @param jsonData
     */
    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "test-consumer-group")
    public void listener(String jsonData) {
        try {
            InvoiceView invoiceView = objectMapper.readValue(jsonData, InvoiceView.class);
            dbService.saveInvoice(invoiceView);
        } catch (JsonProcessingException e) {
            logger.info("Exception: %s occurred while processing json: %s ".formatted(e.toString(), jsonData));
        } catch (RuntimeException e) {
            logger.info("Exception: %s occurred while saving Invoice: %s ".formatted(e.toString(), jsonData));
        }
    }
}
