package com.invoice.Storage.repository;

import com.invoice.Storage.models.db.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceDBRepository extends JpaRepository<InvoiceEntity, Long> {
    Optional<InvoiceEntity> findByInvoiceNumber(String invoiceNumber);
}