package com.invoice.Storage.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.invoice.Storage.models.db.InvoiceEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class InvoiceView {
    private String to;
    private String address;
    private String product;
    private BigDecimal price;
    private BigDecimal tax;
    @JsonSerialize(using = LocalDateSerializer.class) // Explicitly specifying serializer for jakson objectMapper
    @JsonDeserialize(using = LocalDateDeserializer.class) // Explicitly specifying deserializer for jakson objectMapper
    private LocalDate invoiceDate;

    private String invoiceNumber;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public InvoiceView() {
    }

    public InvoiceView(InvoiceEntity invoiceEntity) {
        this.to = invoiceEntity.getTo();
        this.address = invoiceEntity.getAddress();
        this.product = invoiceEntity.getProduct();
        this.price = invoiceEntity.getPrice();
        this.tax = invoiceEntity.getTax();
        this.invoiceDate = invoiceEntity.getInvoiceDate().toLocalDate();
        this.invoiceNumber = invoiceEntity.getInvoiceNumber();
    }
}
