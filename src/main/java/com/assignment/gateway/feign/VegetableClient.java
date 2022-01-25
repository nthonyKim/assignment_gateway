package com.assignment.gateway.feign;

import com.assignment.gateway.dto.ProductDTO;
import com.assignment.gateway.dto.ToKenDTO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.List;

@FeignClient(name = "vegetable", url = "/")
public interface VegetableClient {
    @GetMapping("/token")
    Response getToken(URI uri);

    @GetMapping("/item")
    List<String> getProductList(URI uri, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization);

    @GetMapping("/item")
    ProductDTO getProduct(URI uri,
                          @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                          @RequestParam String name);
}
