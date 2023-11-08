package com.lgf.junit5examples.controllers;

import com.lgf.junit5examples.dto.Invoice;
import com.lgf.junit5examples.services.InvoicesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/invoices")
public class InvoiceResource {

    private InvoicesService invoicesService;

    @RequestMapping("/{id}")
    public Invoice getInvoice(@PathVariable Long id) {
        return invoicesService.getInvoice(id).get();
    }
}


