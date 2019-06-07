package com.epam.keikom.web.converter;

import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.web.dto.EventDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EventToDTO implements Function<Event, EventDTO> {

	@Override
	public EventDTO apply(Event event) {

		final EventDTO dto = new EventDTO();
		dto.setId(event.getId());
		dto.setName(event.getName());
		dto.setBasePrice(event.getBasePrice());
		dto.setRating(event.getId().toString());

		return dto;
	}
}
