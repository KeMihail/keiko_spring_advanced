package com.epam.keikom.service.aspects;

import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.dao.domain.Ticket;
import com.epam.keikom.dao.domain.aspect.CounterAspectDomain;
import com.epam.keikom.service.aspect.ICounterAspectService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Component
@Aspect
public class CounterAspect {

    @Resource(name = "counterAspectService")
    private ICounterAspectService aspectService;

    @Pointcut("execution(* com.epam.keikom.service.impl.EventService.getByName(..))")
    public void pointCupByName() {

    }

    @AfterReturning(pointcut = "pointCupByName()", returning = "retVal")
    public void adviceByName(final Collection retVal) throws NoSuchMethodException {

        final ArrayList returnEvents = new ArrayList(retVal);
        final Event event = (Event) returnEvents.get(0);

        final CounterAspectDomain aspectDomain = getCounterAspect(event);

        if (aspectDomain.getByName().equals(Integer.valueOf(0))) {
            aspectDomain.setByName(1);
        } else {
            aspectDomain.setByName(aspectDomain.getByName() + 1);
        }
        aspectService.update(aspectDomain);
    }

    @Pointcut("execution(* ua.epam.spring.hometask.service.impl.BookingService.getTicketsPrice(..))")
    public void pointCupByPrice() {

    }

    @AfterReturning(pointcut = "pointCupByPrice() && args(event,date,user,seats)")
    public void adviceByPrice(JoinPoint joinPoint, Event event, Object date, Object user, Object seats) {

        final CounterAspectDomain aspectDomain = getCounterAspect(event);

        if (aspectDomain.getByPrice().equals(Integer.valueOf(0))) {
            aspectDomain.setByPrice(1);
        } else {
            aspectDomain.setByPrice(aspectDomain.getByPrice() + 1);
        }
        aspectService.update(aspectDomain);
    }

    @Pointcut("execution(* com.epam.keikom.service.impl.BookingService.bookTickets(..))")
    public void pointCupByBooked() {

    }

    @AfterReturning(pointcut = "pointCupByBooked() && args(tickets)")
    public void adviceByBooked(final Set<Ticket> tickets) {

        for (final Ticket ticket : tickets) {
            Event event = ticket.getEvent();

            final CounterAspectDomain aspectDomain = getCounterAspect(event);

            if (aspectDomain.getByBooked().equals(Integer.valueOf(0))) {
                aspectDomain.setByBooked(1);
            } else {
                aspectDomain.setByBooked(aspectDomain.getByBooked() + 1);
            }
            aspectService.update(aspectDomain);
        }
    }

    private CounterAspectDomain getCounterAspect(final Event event) {

        CounterAspectDomain counterAspect = aspectService.getByEvent(event);

        if (counterAspect == null) {
            aspectService.save(event);
            counterAspect = new CounterAspectDomain();
            counterAspect.setEvent(event.getId());
        }

        return counterAspect;
    }
}
