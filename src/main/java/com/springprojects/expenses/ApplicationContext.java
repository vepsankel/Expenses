package com.springprojects.expenses;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:application.properties")
@Component
public class ApplicationContext {

    @Value("${savefile}")
    String saveFileAddress;

    @Bean("table")
    Table getTable(){
        return new Table(saveFileAddress);
    }
}
