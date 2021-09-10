package ru.vvpetrov91.ftgo.orderservice.application.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"ru.vvpetrov91.ftgo.orderservice"})
@EnableAutoConfiguration
@Import({OrderServiceConfiguration.class})
public class OrderServiceWithRepositoriesConfiguration {
}