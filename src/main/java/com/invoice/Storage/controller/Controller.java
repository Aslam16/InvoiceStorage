package com.invoice.Storage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.invoice.Storage.kafka.InvoiceProducer;
import com.invoice.Storage.models.InvoiceView;
import com.invoice.Storage.service.InvoiceDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.logging.Logger;

/**
 * Rest client to handle the API calls
 */
@RestController
@RequestMapping("/")
public class Controller {
    private Logger logger = Logger.getLogger(Controller.class.getName());
    private final InvoiceDBService invoiceDBService;
    private final InvoiceProducer producer;

    @Autowired
    public Controller(InvoiceDBService invoiceDBService, InvoiceProducer producer) {
        this.invoiceDBService = invoiceDBService;
        this.producer = producer;
    }

    @PostMapping("invoice")
    public boolean createInvoice(@RequestBody InvoiceView invoiceView) throws JsonProcessingException {
        logger.info("Sending Invoice to kafka: %s".formatted(invoiceView.getInvoiceNumber()));
        producer.sendInvoiceToKafka(invoiceView);
        return true;
    }

    @GetMapping("invoice/{invoice_number}")
    public InvoiceView getInvoiceByInvoiceNumber(@PathVariable("invoice_number") String invoiceNumber) {
        InvoiceView invoice = invoiceDBService.getInvoice(invoiceNumber);
        if(invoice == null) {
            String errMsg = "InvoiceNumber: %s does not found".formatted(invoiceNumber);
            logger.info(errMsg);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, errMsg
            );
        }
        return invoice;
    }

    @GetMapping("invoice")
    public List<InvoiceView> getAllInvoices(){
        return invoiceDBService.getInvoices();
    }

    @PutMapping("invoice")
    public InvoiceView updateInvoice(@RequestBody InvoiceView invoiceView) {
        InvoiceView updateInvoice = invoiceDBService.updateInvoice(invoiceView);
        if(updateInvoice == null) {
            String errMsg = "InvoiceNumber: %s does not found".formatted(invoiceView.getInvoiceNumber());
            logger.info(errMsg);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, errMsg
            );
        }
        logger.info("InvoiceNumber: %s updated".formatted(invoiceView.getInvoiceNumber()));
        return updateInvoice;
    }

    @DeleteMapping("invoice/{invoice_number}")
    public InvoiceView deleteInvoice(@PathVariable("invoice_number") String invoiceNumber) {
        InvoiceView invoice = invoiceDBService.deleteInvoice(invoiceNumber);
        if(invoice == null) {
            String errMsg = "InvoiceNumber: %s does not found".formatted(invoiceNumber);
            logger.info(errMsg);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, errMsg
            );
        }
        logger.info("InvoiceNumber: %s deleted".formatted(invoiceNumber));
        return invoice;
    }
}
