package com.epam.keikom.web.converter;

import com.epam.keikom.dao.domain.Ticket;
import com.epam.keikom.service.IEventService;
import com.epam.keikom.web.dto.TicketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TicketToDTO implements Function<Ticket, TicketDTO> {

	@Autowired
	private IEventService eventService;
	@Override
	public TicketDTO apply(Ticket ticket) {

		final TicketDTO dto = new TicketDTO();
		dto.setId(ticket.getId());
		dto.setDateTime(ticket.getDateTime());
		dto.setSeat(ticket.getSeat());
		dto.setEvent(ticket.getEvent().getId());
		dto.setUser(ticket.getUser());

		return dto;
	}

}
