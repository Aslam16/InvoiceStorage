package com.invoice.Storage.models.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "invoices")
@Setter
@Getter
public class InvoiceEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "invoice_to")
    private String to;

    @Column(name = "address")
    private String address;

    @Column(name = "product")
    private String product;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "tax")
    private BigDecimal tax;

    @Column(name = "invoice_date")
    private Date invoiceDate;

    @Column(unique=true, name = "invoice_number")
    private String invoiceNumber;

}
/*

CREATE TABLE invoices (
id SERIAL PRIMARY KEY NOT NULL,
invoice_to VARCHAR (124) NOT NULL,
address VARCHAR (512) NOT NULL,
product VARCHAR (124) NOT NULL,
price NUMERIC (10,2) NOT NULL,
tax NUMERIC (10,2) NOT NULL,
invoice_date Date  NOT NULL,
invoice_number VARCHAR(124) NOT NULL unique
);
 */