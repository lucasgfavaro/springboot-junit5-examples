package com.lgf.junit5examples.services;

import com.lgf.junit5examples.dto.Invoice;
import com.lgf.junit5examples.repositories.InvoiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        Assertions.assertAll("Invoice is not equal to the selected one",
                () -> Assertions.assertNotNull(invoice.get().getId()),
                () -> Assertions.assertEquals(3L, invoice.get().getId()));
        verify(invoiceRepository, times(1)).listAll();
    }

    @Test
    void Given_NonExistentInvoiceId_When_GetInvoiceIsCalled_Then_EmptyOptionalInvoiceIsReturned() {
        //Arrange
        InvoiceRepository invoiceRepository = Mockito.spy(new InvoiceRepository());
        InvoicesService invoicesService = new InvoicesService(invoiceRepository);

        //Act
        Optional<Invoice> invoice = invoicesService.getInvoice(5L);

        //Assert
        assertFalse(invoice.isPresent());
        verify(invoiceRepository, times(1)).listAll();
    }

    @Test
    void Given_InvalidInvoiceId_When_GetInvoiceIsCalled_Then_ExceptionIsThrown(@Mock InvoiceRepository invoiceRepository) {
        //Arrange
        InvoicesService invoicesService = new InvoicesService(invoiceRepository);

        //Act & Assert
        Exception thrownException = Assertions.assertThrows(IllegalArgumentException.class, () -> invoicesService.getInvoice(20L), "Exception not thrown");
        assertEquals("Invalid Id", thrownException.getMessage(), "Exception message does not match");
        verifyNoInteractions(invoiceRepository);
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