package com.maurigvs.bank.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Utils {

    public static String ofJson(Object object) {
        try {
            ObjectMapper om = new ObjectMapper();
            om.registerModule(new JavaTimeModule());
            return om.writeValueAsString(object);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
