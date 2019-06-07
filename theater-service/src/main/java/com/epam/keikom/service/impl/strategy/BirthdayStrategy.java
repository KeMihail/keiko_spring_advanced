package com.epam.keikom.service.impl.strategy;

import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.dao.domain.User;
import com.epam.keikom.service.IDiscountService;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class BirthdayStrategy implements IDiscountService {

    private static final Integer DISCOUNT = Integer.valueOf(5);

    @Override
    public Double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {

        final Double discount = ((event.getBasePrice() * numberOfTickets) / 100) * DISCOUNT;

        return discount;
    }
}
