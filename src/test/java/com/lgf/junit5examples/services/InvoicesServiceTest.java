package com.lgf.junit5examples.services;

import com.lgf.junit5examples.dto.Invoice;
import com.lgf.junit5examples.repositories.InvoiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
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

    @Test
    void Given_ValidExistentInvoiceId_When_GetInvoiceIsCalled_Then_NonEmptyOptionalInvoiceIsReturned(@Mock InvoiceRepository invoiceRepository) {
        //Arrange
        InvoicesService invoicesService = new InvoicesService(invoiceRepository);
        when(invoiceRepository.listAll()).thenReturn(generateFakeInvoiceList());

        //Act
        Optional<Invoice> invoice = invoicesService.getInvoice(3L);

        //Assert
        assertTrue(invoice.isPresent());
        assertNotNull(invoice.get().getId());
        assertEquals(3L, invoice.get().getId());
    }

    @Test
    void Given_NonExistentInvoiceId_When_GetInvoiceIsCalled_Then_EmptyOptionalInvoiceIsReturned(@Mock InvoiceRepository invoiceRepository) {
        //Arrange
        InvoicesService invoicesService = new InvoicesService(invoiceRepository);

        //Act
        Optional<Invoice> invoice = invoicesService.getInvoice(5L);

        //Assert
        assertFalse(invoice.isPresent());
    }

    @Test
    void Given_InvalidInvoiceId_When_GetInvoiceIsCalled_Then_ExceptionIsThrown() {
        //Arrange
        InvoicesService invoicesService = new InvoicesService(new InvoiceRepository());

        //Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Optional<Invoice> invoice = invoicesService.getInvoice(20L);
        });
    }

    @Test
    @Disabled
    void Given_DISABLED_When_XXX_Then_XXX() {
        // SUT
        //Arrange
        //Act
        //Assert
        assertEquals(1, 2);
    }

    private List<Invoice> generateFakeInvoiceList() {
        return Arrays.asList(new Invoice(3L), new Invoice(4L));
    }
}