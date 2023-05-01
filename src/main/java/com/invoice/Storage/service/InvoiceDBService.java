package com.invoice.Storage.service;

import com.invoice.Storage.models.InvoiceView;
import com.invoice.Storage.models.db.InvoiceEntity;
import com.invoice.Storage.repository.InvoiceDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * Access point to the invoice table in database
 */
@Service
public class InvoiceDBService {
    private final InvoiceDBRepository dbRepository;

    private InvoiceEntity createInvoiceEntityFromView(InvoiceView invoiceView) {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setAddress(invoiceView.getAddress());
        invoiceEntity.setTo(invoiceView.getTo());
        invoiceEntity.setPrice(invoiceView.getPrice());
        invoiceEntity.setTax(invoiceView.getTax());
        invoiceEntity.setProduct(invoiceView.getProduct());
        invoiceEntity.setInvoiceNumber(invoiceView.getInvoiceNumber());
        invoiceEntity.setInvoiceDate(Date.valueOf(invoiceView.getInvoiceDate()));
        return invoiceEntity;
    }

    @Autowired
    public InvoiceDBService(InvoiceDBRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    public void saveInvoice(InvoiceView invoiceView) {
        InvoiceEntity entity = createInvoiceEntityFromView(invoiceView);
        dbRepository.save(entity);
    }

    public InvoiceView getInvoice(String invoiceNumber) {
        InvoiceEntity entity;
        entity = getInvoiceEntity(invoiceNumber);
        if (entity == null) {
            return null;
        }
        return new InvoiceView(entity);
    }

    private InvoiceEntity getInvoiceEntity(String invoiceNumber) {
        InvoiceEntity entity = null;
        if (dbRepository.findByInvoiceNumber(invoiceNumber).isPresent()) {
            entity = dbRepository.findByInvoiceNumber(invoiceNumber).get();
        }
        return entity;
    }

    public List<InvoiceView> getInvoices() {
        List<InvoiceEntity> entityList = dbRepository.findAll();
        return entityList.stream().map(InvoiceView::new).toList();
    }

    public InvoiceView updateInvoice(InvoiceView invoiceView) {
        InvoiceEntity invoiceEntity = getInvoiceEntity(invoiceView.getInvoiceNumber());
        if (invoiceEntity == null) {
            // no invoice with current invoiceNumber
            return null;
        }
        if (invoiceView.getInvoiceDate() != null) {
            invoiceEntity.setInvoiceDate(Date.valueOf(invoiceView.getInvoiceDate()));
        }
        if (invoiceView.getTax() != null) {
            invoiceEntity.setTax(invoiceView.getTax());
        }
        if (invoiceView.getTo() != null) {
            invoiceEntity.setTo(invoiceView.getTo());
        }
        if (invoiceView.getAddress() != null) {
            invoiceEntity.setAddress(invoiceView.getAddress());
        }
        if (invoiceView.getPrice() != null) {
            invoiceEntity.setPrice(invoiceView.getPrice());
        }
        if (invoiceView.getProduct() != null) {
            invoiceEntity.setProduct(invoiceView.getProduct());
        }
        dbRepository.save(invoiceEntity);
        return new InvoiceView(invoiceEntity);
    }

    public InvoiceView deleteInvoice(String invoiceNumber) {
        InvoiceEntity invoiceEntity = getInvoiceEntity(invoiceNumber);
        if (invoiceEntity == null) {
            return null;
        }
        dbRepository.deleteById(invoiceEntity.getId());
        return new InvoiceView(invoiceEntity);
    }
}
