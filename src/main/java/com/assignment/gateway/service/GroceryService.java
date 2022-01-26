package com.assignment.gateway.service;

import com.assignment.gateway.dto.ProductDTO;
import com.assignment.gateway.dto.ResponseDTO;
import com.assignment.gateway.dto.ToKenDTO;
import com.assignment.gateway.exception.NotFoundException;
import com.assignment.gateway.feign.FruitClient;
import com.assignment.gateway.feign.VegetableClient;
import com.assignment.gateway.type.ProductType;
import com.assignment.gateway.util.CookieUtils;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.*;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GroceryService {
    private final VegetableClient vegetableClient;
    private final FruitClient fruitClient;

    @Value("${api.fruit.host}") private String fruitApiHost;
    @Value("${api.vegetable.host}") private String vegetableApiHost;

    @PostConstruct
    private void init(){
        // 암호화되어 있으므로 생성과 함께 복호화 처리
        fruitApiHost = new String(Base64.getDecoder().decode(fruitApiHost.getBytes()));
        vegetableApiHost = new String(Base64.getDecoder().decode(vegetableApiHost.getBytes()));
    }

    private ProductDTO getFruitDetail(final String name) throws URISyntaxException {
        URI uri = new URI(fruitApiHost);
        ToKenDTO token = fruitClient.getToken(uri);
        List<String> productList = fruitClient.getProductList(uri, token.getAccessToken());

        if(!productList.contains(name)){
            throw new NotFoundException("대상을 찾을 수 없습니다.");
        }

        return fruitClient.getProduct(uri, token.getAccessToken(), name);
    }

    private ProductDTO getVegetableDetail(final String name) throws URISyntaxException {
        URI uri = new URI(vegetableApiHost);

        // token을 response의 cookie 값으로 받아오므로 token을 파싱해주는 과정이 필요
        Response response = vegetableClient.getToken(uri);
        Map<String, String> parseCookie = CookieUtils.parserCookie(response.headers().get(HttpHeaders.SET_COOKIE));

        String token = parseCookie.get(HttpHeaders.AUTHORIZATION);
        List<String> productList = vegetableClient.getProductList(uri, token);

        if(!productList.contains(name)){
            throw new NotFoundException("대상을 찾을 수 없습니다.");
        }

        return vegetableClient.getProduct(uri, token, name);
    }


    public ResponseDTO findProductInfo(final String productType, final String name) throws URISyntaxException {
        ProductDTO productDTO = null;
        switch (productType) {
            case ProductType.FRUIT: productDTO = getFruitDetail(name); break;
            case ProductType.VEGETABLE: productDTO = getVegetableDetail(name); break;
            default: throw new NotFoundException("잘못된 분류 입니다.");
        }

        return ResponseDTO.success(productDTO);
    }
}
