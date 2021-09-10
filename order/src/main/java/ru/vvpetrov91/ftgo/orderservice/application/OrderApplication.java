package ru.vvpetrov91.ftgo.orderservice.application;

import io.eventuate.tram.spring.jdbckafka.TramJdbcKafkaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import ru.vvpetrov91.ftgo.orderservice.application.configuration.OrderServiceMessagingConfiguration;
import ru.vvpetrov91.ftgo.orderservice.application.configuration.OrderWebConfiguration;
import ru.vvpetrov91.ftgo.orderservice.order.service.command.OrderCommandHandlersConfiguration;

@SpringBootApplication(scanBasePackages = {"ru.vvpetrov91.ftgo.orderservice"})
@EntityScan("ru.vvpetrov91.ftgo.orderservice")
@Import(
        {
                OrderWebConfiguration.class,
                OrderCommandHandlersConfiguration.class,
                OrderServiceMessagingConfiguration.class,
                TramJdbcKafkaConfiguration.class
        }
)
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}