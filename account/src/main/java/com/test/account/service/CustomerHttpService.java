package com.test.account.service;

import com.test.account.model.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class CustomerHttpService implements ICustomerHttpService {

    private final RestTemplate restTemplate;

    @Value("${external.api.url}")
    private String clientApiUrl;

    @Autowired
    public CustomerHttpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CustomerResponse getCustomerByName(String name) {
        try{
            String url = clientApiUrl +  "/customers/find/name?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8);
            System.out.println("url " + url);
            return restTemplate.getForObject(url, CustomerResponse.class);
        }catch (Exception e){
//            System.err.println(e.getMessage());
            throw new IllegalArgumentException("Customer not found");
        }
    }
}
