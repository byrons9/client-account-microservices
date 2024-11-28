package com.test.account.model;

import lombok.Data;

@Data
public class CustomerResponse {
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
