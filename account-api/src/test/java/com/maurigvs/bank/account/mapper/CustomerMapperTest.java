package com.maurigvs.bank.account.mapper;

import com.maurigvs.bank.account.model.Customer;
import com.maurigvs.bank.grpc.CustomerData;
import com.maurigvs.bank.grpc.FindCustomerReply;
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
        var expected = CustomerData.newBuilder().setId(1L).setTaxId("12345").build();
        var reply = FindCustomerReply.newBuilder().setCustomerData(expected).build();

        var result = new CustomerMapper().apply(reply);

        assertInstanceOf(Customer.class, result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getTaxId(), result.getTaxId());
        assertTrue(result.getAccountList().isEmpty());
    }
}