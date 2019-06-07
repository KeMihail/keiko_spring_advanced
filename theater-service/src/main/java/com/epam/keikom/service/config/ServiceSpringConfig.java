package com.epam.keikom.service.config;

import com.epam.keikom.dao.config.DaoSpringConfig;
import com.epam.keikom.service.ICurrentStrategy;
import com.epam.keikom.service.IDiscountService;
import com.epam.keikom.service.impl.strategy.BirthdayOrTicketDiscountStrategy;
import com.epam.keikom.service.impl.strategy.BirthdayStrategy;
import com.epam.keikom.service.impl.strategy.TicketsStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "com.epam.keikom.service")
@Import(DaoSpringConfig.class)
public class ServiceSpringConfig {

    @Bean
    public IDiscountService birthdayStrategy()
    {
        return new BirthdayStrategy();
    }

    @Bean
    public IDiscountService ticketsStrategy()
    {
        return new TicketsStrategy();
    }

    @Bean
    public ICurrentStrategy birthdayOrTicket()
    {
        return new BirthdayOrTicketDiscountStrategy();
    }

    @Bean
    public Map<String, IDiscountService> discountStrategies()
    {
        final Map<String,IDiscountService> strategies = new HashMap<>();
        strategies.put("Birthday",birthdayStrategy());
        strategies.put("Ticket",ticketsStrategy());
        strategies.put("Discount",birthdayOrTicket());

        return strategies;
    }
}
