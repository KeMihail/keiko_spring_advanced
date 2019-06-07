package com.epam.keikom.web.controller;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.dao.domain.Ticket;
import com.epam.keikom.dao.exceptions.AmbiguousIdentifierException;
import com.epam.keikom.dao.exceptions.UnknownIdentifierException;
import com.epam.keikom.service.IBookingService;
import com.epam.keikom.service.IEventService;
import com.epam.keikom.service.ITicketService;
import com.epam.keikom.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/booking")
public class BookedController {

	@Autowired
	private IBookingService bookingService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ITicketService ticketService;

	@Autowired
	private IEventService eventService;

	@RequestMapping("/user/{id}")
	public ModelAndView showForm(@PathVariable("id") final Long id, HttpServletRequest request)
			throws UnknownIdentifierException, AmbiguousIdentifierException {

		request.getSession().setAttribute("userId", id);
		final List<Ticket> tickets = new ArrayList<>(ticketService.getAll());
		final Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("tickets", tickets);
		map.put("tick", new ArrayList<Ticket>());

		return new ModelAndView("bookTickets", map);
	}

	@RequestMapping(value = "/tickets", method = RequestMethod.POST)
	public String bookTicket(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws UnknownIdentifierException, AmbiguousIdentifierException {

		final List<String> tickets = Arrays.asList(request.getParameterValues("item"));

		final Set<Ticket> ticketSet = new HashSet<>();
		for (String id : tickets) {
			final Ticket ticket = ticketService.getById(Long.valueOf(id));
			ticketSet.add(ticket);
		}

		final Object id = (session.getAttribute("userId"));
		session.removeAttribute("userId");

		bookingService.bookTickets(ticketSet, userService.getById(Long.parseLong(id.toString())));
		return "redirect:/user/usersList";
	}

	@RequestMapping("/purchased/{date}")
	public ModelAndView purchasedTickets(@PathVariable("date") final String date, HttpSession session)
			throws UnknownIdentifierException {

		final Map<String, Object> map = new HashMap<>();

		final Object id = (session.getAttribute("eventId"));

		// ???
		session.removeAttribute("eventId");

		final Event event = eventService.getById(Long.parseLong(id.toString()));
		final LocalDateTime dateTime = LocalDateTime.parse(date);

		map.put("tickets", bookingService.getPurchasedTicketsForEvent(event, dateTime));
		map.put("viewAll", Boolean.FALSE);

		return new ModelAndView("ticketsList", map);
	}
}
