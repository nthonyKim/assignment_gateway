package com.assignment.gateway.controller;

import com.assignment.gateway.dto.ResponseDTO;
import com.assignment.gateway.service.GroceryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grocery")
public class GroceryController {
    private final GroceryService groceryService;

    @GetMapping
    public ResponseDTO findProductInfo(@RequestParam String productType, @RequestParam String name) throws URISyntaxException {
        return groceryService.findProductInfo(productType, name);
    }

}
