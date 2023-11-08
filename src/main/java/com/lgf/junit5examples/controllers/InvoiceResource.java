package com.lgf.junit5examples.controllers;

import com.lgf.junit5examples.dto.Invoice;
import com.lgf.junit5examples.services.InvoicesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/invoices")
public class InvoiceResource {

    private InvoicesService invoicesService;

    @RequestMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable Long id) {
        Optional<Invoice> invoice = invoicesService.getInvoice(id);
        return invoice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
}


