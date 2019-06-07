package com.epam.keikom.service.impl;

import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.dao.exceptions.UnknownIdentifierException;
import com.epam.keikom.dao.jdbc.IEventDao;
import com.epam.keikom.dao.jdbc.ITicketDao;
import com.epam.keikom.service.IEventService;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class EventService implements IEventService {

    @Resource(name = "eventDao")
    IEventDao eventDao;

    @Resource(name = "ticketDao")
    private ITicketDao ticketDao;

    public void setEventDao(IEventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Nullable
    @Override
    public Collection<Event> getByName(@Nonnull final String name) {
        return eventDao.getByName(name);
    }

    @Nonnull
    @Override
    public Collection<Event> getForDateRange(@Nonnull final LocalDateTime from, @Nonnull final LocalDateTime to) {
        return eventDao.getForDateRange(from, to);
    }

    @Nonnull
    @Override
    public Collection<Event> getNextEvents(@Nonnull final LocalDateTime to) {
        return eventDao.getNextEvents(to);
    }

    @Nonnull
    @Override
    public Collection<LocalDateTime> getAirDates(@Nonnull Long eventId) {
        return ticketDao.getDateFromEvent(eventId);
    }

    @Override
    public Event save(@Nonnull final Event event) {
        if (event.getId() != null) {
            eventDao.update(event);
        } else {
            event.setId(eventDao.save(event).getId());
        }
        return event;
    }

    @Override
    public void remove(@Nonnull final Event object) {
        eventDao.remove(object);
    }

    @Override
    public Event getById(@Nonnull final Long id) throws UnknownIdentifierException {

        final Event event = eventDao.getById(id);

        if (event == null) {
            throw new UnknownIdentifierException("Event with id '" + id + " not found!");
        }

        return event;
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return eventDao.getAll();
    }
}
