package com.lgf.junit5examples.controllers;

import com.lgf.junit5examples.dto.Invoice;
import com.lgf.junit5examples.services.InvoicesService;
import com.lgf.junit5examples.services.ListShrinker;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/listShrinker")
public class ListShrinkerResource {



    @RequestMapping()
    public String shrinkList(@RequestParam String list) {
        ListShrinker listShrinker = ListShrinker.build(",","-");
        return listShrinker.shrink(toIntegerList(list));
    }

    private List<Integer> toIntegerList(String inputValues) {
        return Arrays.asList(inputValues.split(",")).stream().map(o -> Integer.parseInt(o)).collect(Collectors.toList());
    }
}


