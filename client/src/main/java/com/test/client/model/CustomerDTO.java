package com.test.client.model;

import lombok.Data;

@Data
public class CustomerDTO {
    Long id;
    Integer age;
    String name;
    String gender;
    String identification;
    String address;
    String phone;
    String password;
    Boolean status;
}
