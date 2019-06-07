package com.epam.keikom.service.impl.strategy;

import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.dao.domain.User;
import com.epam.keikom.service.ICurrentStrategy;
import com.epam.keikom.service.IDiscountService;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;

import java.time.LocalDateTime;

public class BirthdayOrTicketDiscountStrategy implements ICurrentStrategy {

    @Resource(name = "birthdayStrategy")
    private IDiscountService birthdayStrategy;

    @Resource(name = "ticketsStrategy")
    private IDiscountService ticketsStrategy;

    private static final String BIRTHDAY_STRATEGY = "birthdayStrategy";
    private static final String TICKET_STRATEGY = "ticketStrategy";
    private Double birthdayDiscount = 0.0;
    private Double ticketsDiscount = 0.0;

    public void setBirthdayStrategy(IDiscountService birthdayStrategy) {
        this.birthdayStrategy = birthdayStrategy;
    }

    public void setTicketsStrategy(IDiscountService ticketsStrategy) {
        this.ticketsStrategy = ticketsStrategy;
    }

    @Override
    public String currentStrategy() {
        if (birthdayDiscount > ticketsDiscount) {
            return BIRTHDAY_STRATEGY;
        } else {
            return TICKET_STRATEGY;
        }
    }

    @Override
    public Double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        birthdayDiscount = birthdayStrategyAmount(user, event, airDateTime, numberOfTickets);
        ticketsDiscount = ticketStrategyAmount(user, event, airDateTime, numberOfTickets);

        if (birthdayDiscount >= ticketsDiscount) {
            return birthdayDiscount;
        }
        return ticketsDiscount;
    }

    private Double birthdayStrategyAmount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        return birthdayStrategy.getDiscount(user, event, airDateTime, numberOfTickets);
    }

    private Double ticketStrategyAmount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        return ticketsStrategy.getDiscount(user, event, airDateTime, numberOfTickets);
    }
}
