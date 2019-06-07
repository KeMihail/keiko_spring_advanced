package com.epam.keikom.service.impl;

import com.epam.keikom.dao.domain.*;
import com.epam.keikom.dao.exceptions.UnknownIdentifierException;
import com.epam.keikom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Service
public class BookingService implements IBookingService {

	private final static Double RAITING_HIGN = Double.valueOf(1.2);
	private static final Double VIP_SEATS = Double.valueOf(1.5);
	private static final String BIRHDAY_DISCOUNT_STRATEGY = "Birthday";
	private static final String TICKET_DISCOUNT_STRATEGY = "Ticket";
	private static final String DISCOUNT_STRATEGY = "Discount";

	@Resource(name = "userService")
	private IUserService userService;

	@Autowired
	private ITicketService ticketService;

	@Resource(name = "birthdayOrTicket")
	private ICurrentStrategy currentStrategy;

	private IDiscountService discountService;

	@Resource(name = "discountStrategies")
	private Map<String, IDiscountService> strategies;

	public void setStrategies(Map<String, IDiscountService> strategies) {
		this.strategies = strategies;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public double getTicketsPrice(@Nonnull final Event event, @Nonnull final LocalDateTime dateTime,
			@Nullable final User user, @Nonnull final Set<Long> seats) {
		Double result = event.getBasePrice() * seats.size();
		if (event.getRating().equals(EventRating.HIGH)) {
			result *= seats.size() * RAITING_HIGN;
		}

		final Map<LocalDateTime, Auditorium> auditoriums = event.getAuditoriums();
		final Auditorium auditorium = auditoriums.get(dateTime);
		final Long countVipSeats = auditorium.countVipSeats(seats);

		if (isBirthday(dateTime, user) && (seats.size() < Long.valueOf(10))) {
			discountService = strategies.get(BIRHDAY_DISCOUNT_STRATEGY);
		}

		if (seats.size() > Long.valueOf(9) && !isBirthday(dateTime, user)) {
			discountService = strategies.get(TICKET_DISCOUNT_STRATEGY);
		}

		if (isBirthday(dateTime, user) && (seats.size() > Long.valueOf(9))) {

			discountService = strategies.get(DISCOUNT_STRATEGY);
		}

		if (discountService != null) {
			result -= discountService.getDiscount(user, event, dateTime, seats.size());
		}

		if (discountService instanceof ICurrentStrategy) {
			currentStrategy.currentStrategy();
		}

		if (countVipSeats != null) {
			result += (event.getBasePrice() * (VIP_SEATS - 1)) * countVipSeats;
		}
		return result;
	}

	@Override
	public void bookTickets(@Nonnull final Set<Ticket> tickets, @Nonnull final User user)
			throws UnknownIdentifierException {

		for (final Ticket ticket : tickets) {

			ticket.setUser(user);
			ticketService.save(ticket);
		}
	}

	@Nonnull
	@Override
	public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull final Event event, @Nonnull final LocalDateTime dateTime) {

		final List<Ticket> tickets = new ArrayList<>(ticketService.getAll());
		final Set<Ticket> result = new HashSet<>();

		for (final Ticket ticket : tickets) {
			if (ticket.getDateTime().isEqual(dateTime) && ticket.getEvent().equals(event) && ticket.getUser() != null) {
				result.add(ticket);
			}
		}
		return result;
	}

	private Boolean isBirthday(@Nonnull final LocalDateTime dateTime, @Nullable final User user) {

		if (user.getBirthday() != null) {

			final Month monthBirthday = user.getBirthday().getMonth();
			final Integer dayBirthday = user.getBirthday().getDayOfMonth();

			final Month monthEvent = dateTime.getMonth();
			final Integer dayEvent = dateTime.getDayOfMonth();

			if (monthBirthday.equals(monthEvent)) {
				if (dayBirthday.equals(dayEvent) || (dayBirthday + 5) < dayEvent) {

					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
}
