package ru.vvpetrov91.ftgo.orderservice.application.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.eventuate.common.json.mapper.JSonMapper;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
@Import(OrderServiceWithRepositoriesConfiguration.class)
public class OrderWebConfiguration {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return JSonMapper.objectMapper;
    }
}