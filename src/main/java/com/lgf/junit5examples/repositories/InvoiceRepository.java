package com.lgf.junit5examples.repositories;

import com.lgf.junit5examples.dto.Invoice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;


@Repository
@AllArgsConstructor
public class InvoiceRepository {
    public List<Invoice> listAll() {
        return Arrays.asList(new Invoice(1L), new Invoice(2L));
    }
}
