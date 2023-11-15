package com.lgf.junit5examples.services;

import com.lgf.junit5examples.dto.Invoice;
import com.lgf.junit5examples.repositories.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InvoicesService {

    private InvoiceRepository invoiceRepository;

    public Optional<Invoice> getInvoice(Long id) {

        if (id > 10)
            throw new IllegalArgumentException("Invalid Id");

        return invoiceRepository.listAll().stream().filter(i -> i.getId().equals(id)).findFirst();
    }
}
