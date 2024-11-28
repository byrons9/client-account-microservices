package com.test.account.service;

import com.test.account.model.CustomerResponse;

public interface ICustomerHttpService {
    CustomerResponse getCustomerByName(String name);
}
