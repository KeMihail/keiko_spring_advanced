package com.epam.keikom.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.dao.domain.Ticket;
import com.epam.keikom.dao.exceptions.UnknownIdentifierException;
import com.epam.keikom.service.IEventService;
import com.epam.keikom.service.ITicketService;
import com.epam.keikom.web.converter.DtoToTicket;
import com.epam.keikom.web.converter.TicketToDTO;
import com.epam.keikom.web.dto.TicketDTO;

@Controller
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	private ITicketService ticketService;

	@Autowired
	private IEventService eventService;

	@Autowired
	private DtoToTicket dtoToTicket;

	@Autowired
	private TicketToDTO ticketToDTO;

	@RequestMapping("/ticketsList")
	public ModelAndView gettickets() {
		final Map<String, Object> map = new HashMap<>();
		map.put("tickets", ticketService.getAll());
		map.put("viewAll", Boolean.TRUE);
		return new ModelAndView("ticketsList", map);
	}

	@RequestMapping("/add")
	public ModelAndView showForm() {

		final Map<String, String> mapEvents = new HashMap<>();

		getEvents(mapEvents);

		final Map<String, Object> map = new HashMap<>();
		map.put("events", mapEvents);
		map.put("ticket", new TicketDTO());

		return new ModelAndView("adicket", map);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addTicket(@ModelAttribute("ticket") final TicketDTO dto) {
		final Ticket ticket = dtoToTicket.apply(dto);

		ticketService.save(ticket);
		return "redirect:/ticket/ticketsList";
	}

	@RequestMapping("/update/{id}")
	public ModelAndView updateTicket(@PathVariable("id") final Long id) throws UnknownIdentifierException {

		final TicketDTO dto = ticketToDTO.apply(ticketService.getById(id));

		final Map<String, String> mapEvents = new HashMap<>();

		getEvents(mapEvents);

		final Map<String, Object> map = new HashMap<>();
		map.put("events", mapEvents);
		map.put("ticket", dto);

		return new ModelAndView("editTicket", map);

	}

	private void getEvents(final Map<String, String> map) {

		final List<Event> events = new ArrayList<>(eventService.getAll());

		for (Event event : events) {
			map.put(event.getId().toString(), event.getName());
		}
	}
}
