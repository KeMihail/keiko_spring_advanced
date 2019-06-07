package com.epam.keikom.service.aspects;

import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.dao.domain.User;
import com.epam.keikom.dao.domain.aspect.DiscountAspectDomain;
import com.epam.keikom.service.aspect.IDiscountAspectService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Aspect
@Component
public class DiscountAspect {

    @Resource(name = "discountAspectService")
    private IDiscountAspectService aspectService;

    private Double allDiscountAmount = new Double(0.0);

    private static final String BIRTHDAY_STRATEGY = "birthdayStrategy";
    private static final String TICKET_CLASS = "TicketsStrategy";
    private static final String BIRTHDAY_CLASS = "BirthdayStrategy";

    @Pointcut("execution(* com.epam.keikom.service.impl.strategy.*.getDiscount(..))")
    public void pointCupByDiscount() {

    }

    @AfterReturning(pointcut = "pointCupByDiscount() && args(user,event,airDateTime,numberOfTickets)", returning = "retVal")
    public void adviceByDiscount(final JoinPoint joinPoint, final Double retVal, User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {

        String key = joinPoint.getTarget().getClass().getSimpleName();

        if (key.equals(TICKET_CLASS) || key.equals(BIRTHDAY_CLASS)) {

            final DiscountAspectDomain aspectDomain = getDiscountAspectDomain(key, retVal);
            aspectService.update(aspectDomain);
        }

        key = user.getId().toString();
        final DiscountAspectDomain aspectDomain = getDiscountAspectDomain(key, retVal);
        aspectService.update(aspectDomain);

    }

    private DiscountAspectDomain getDiscountAspectDomain(final String key, final Double retVal) {
        DiscountAspectDomain aspectDomain = aspectService.getByDiscount(key);

        if (aspectDomain == null) {
            aspectDomain = new DiscountAspectDomain();
            aspectDomain.setDiscount(key);
            aspectDomain.setAmount(retVal);
            aspectService.save(aspectDomain);
        } else {
            aspectDomain.setAmount(aspectDomain.getAmount() + retVal);
        }
        return aspectDomain;
    }

}
