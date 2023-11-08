package com.lgf.junit5examples.services;

import com.lgf.junit5examples.dto.Invoice;
import com.lgf.junit5examples.repositories.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvoicesServiceTest {

    InvoicesService invoicesService;

    @BeforeEach
    void init(@Mock InvoiceRepository invoiceRepository) {
        when(invoiceRepository.listAll()).thenReturn(generateInvoiceList());
        invoicesService = new InvoicesService(invoiceRepository);
    }

    private List<Invoice> generateInvoiceList() {

        return Arrays.asList(new Invoice(3L), new Invoice(4L));
    }

    @Test
    void getInvoice() {
        Optional<Invoice> invoice = invoicesService.getInvoice(3L);
        assertTrue(invoice.isPresent());
        assertNotNull(invoice.get().getId());
        assertEquals(3L,invoice.get().getId());
    }


}