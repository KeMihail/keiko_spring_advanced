package com.epam.keikom.web.controller;

import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.dao.domain.EventRating;
import com.epam.keikom.dao.exceptions.UnknownIdentifierException;
import com.epam.keikom.service.IBookingService;
import com.epam.keikom.service.IEventService;
import com.epam.keikom.web.converter.DtoToEvent;
import com.epam.keikom.web.converter.EventToDTO;
import com.epam.keikom.web.dto.EventDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/event")
public class EventController {

	@Autowired
	private IEventService eventService;

	@Autowired
	private IBookingService bookingService;

	@Autowired
	private DtoToEvent dtoToEvent;

	@Autowired
	private EventToDTO eventToDTO;

	@RequestMapping("/eventsList")
	public ModelAndView getEvents(final Model model) {
		return new ModelAndView("eventList", "events", eventService.getAll());
	}

	@RequestMapping("/add")
	public ModelAndView showForm() {

		final List<EventRating> eventRating = Arrays.asList(EventRating.class.getEnumConstants());

		final Map<String, Object> map = new HashMap<>();
		map.put("ratings", eventRating);
		map.put("event", new EventDTO());

		return new ModelAndView("addEvent", map);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addEvent(@ModelAttribute("event") final EventDTO eventDTO) {
		final Event event = dtoToEvent.apply(eventDTO);
		eventService.save(event);
		return "redirect:/event/eventsList";
	}

	@RequestMapping("/update/{id}")
	public ModelAndView updateEvent(@PathVariable("id") final Long id) throws UnknownIdentifierException {

		final Event event = eventService.getById(id);
		final EventDTO eventDTO = eventToDTO.apply(event);

		final List<EventRating> eventRating = Arrays.asList(EventRating.class.getEnumConstants());

		final Map<String, Object> map = new HashMap<>();
		map.put("ratings", eventRating);
		map.put("event", eventDTO);

		return new ModelAndView("editEvent", map);
	}

	@RequestMapping("/remove/{id}")
	public String removeEvent(@PathVariable("id") final Long id) throws UnknownIdentifierException {
		eventService.remove(eventService.getById(id));
		return "redirect:/event/eventsList";
	}

	@RequestMapping("/airDates/{id}")
	public ModelAndView purchasedTickets(@PathVariable("id") final Long id, HttpServletRequest request)
			throws UnknownIdentifierException {

		request.getSession().setAttribute("eventId", id);

		return new ModelAndView("ticketAirDates", "airDates",
				eventService.getAirDates(eventService.getById(id).getId()));
	}

}
