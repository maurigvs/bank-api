package com.maurigvs.bank.account.mapper;

import com.maurigvs.bank.CustomerReply;
import com.maurigvs.bank.account.model.Customer;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CustomerMapperTest {

    @Test
    void should_return_Customer_given_an_CustomerReply() {
        var customerReply = CustomerReply.newBuilder()
                .setId(1L)
                .setTaxId("12345")
                .build();

        var result = new CustomerMapper().apply(customerReply);

        assertInstanceOf(Customer.class, result);
        assertEquals(customerReply.getId(), result.getId());
        assertEquals(customerReply.getTaxId(), result.getTaxId());
        assertTrue(result.getAccountList().isEmpty());
    }
}