package com.team2.supermarket.controller;


import com.team2.supermarket.dto.SaleDto;
import com.team2.supermarket.service.Implementation.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sale")

public class SaleController {


    @Autowired
    private SaleService saleService;

    public ResponseEntity<String> makeSale(@RequestBody @Valid SaleDto saleDto) {
        saleService.makeSale(saleDto);
        return ResponseEntity.ok("Sale Completed Succesfully");
    }

}
