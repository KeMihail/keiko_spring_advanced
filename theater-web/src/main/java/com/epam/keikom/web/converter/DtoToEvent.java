package com.epam.keikom.web.converter;

import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.dao.domain.EventRating;
import com.epam.keikom.web.dto.EventDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DtoToEvent implements Function<EventDTO, Event> {

	@Override
	public Event apply(EventDTO dto) {

		final Event event = new Event();
		event.setId(dto.getId());
		event.setName(dto.getName());
		event.setBasePrice(dto.getBasePrice());
		event.setRating(EventRating.valueOf(dto.getRating()));

		return event;
	}
}
