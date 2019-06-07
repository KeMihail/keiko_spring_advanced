package com.epam.keikom.web.converter;

import com.epam.keikom.dao.domain.Ticket;
import com.epam.keikom.dao.exceptions.UnknownIdentifierException;
import com.epam.keikom.service.IEventService;
import com.epam.keikom.web.dto.TicketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DtoToTicket implements Function<TicketDTO, Ticket> {

	@Autowired
	private IEventService eventService;

	@Override
	public Ticket apply(TicketDTO dto) {

		final Ticket ticket = new Ticket();
		ticket.setId(dto.getId());
		ticket.setDateTime(dto.getDateTime());
		ticket.setSeat(dto.getSeat());
		try {
			ticket.setEvent(eventService.getById(dto.getEvent()));
		} catch (UnknownIdentifierException e) {

		}
		ticket.setUser(dto.getUser());

		return ticket;
	}

}
