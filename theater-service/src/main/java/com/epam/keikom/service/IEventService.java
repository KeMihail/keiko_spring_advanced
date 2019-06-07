package com.epam.keikom.service;

import com.epam.keikom.dao.domain.Event;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * @author Yuriy_Tkach
 */
public interface IEventService extends IAbstractDomainObjectService<Event> {

    /**
     * Finding event by name
     * 
     * @param name
     *            Name of the event
     * @return found event or <code>null</code>
     */
    public @Nullable
    Collection<Event> getByName(@Nonnull String name);

    /*
     * Finding all events that air on specified date range
     * 
     * @param from Start date
     * 
     * @param to End date inclusive
     * 
     * @return Set of events
     */
    // public @Nonnull Set<Event> getForDateRange(@Nonnull LocalDate from,
    // @Nonnull LocalDate to);

    /*
     * Return events from 'now' till the the specified date time
     * 
     * @param to End date time inclusive
     * s
     * @return Set of events
     */
    // public @Nonnull Set<Event> getNextEvents(@Nonnull LocalDateTime to);

    public @Nonnull
    Collection<Event> getForDateRange(@Nonnull LocalDateTime from, @Nonnull LocalDateTime to);

    /**
     * Finding next events by date
     * @param to
     * 		Date to
     * @return returns events from now till the ‘to’ date
     */
    public @Nonnull
    Collection<Event> getNextEvents(@Nonnull LocalDateTime to);

    public @Nonnull
    Collection<LocalDateTime> getAirDates(@Nonnull Long eventId);
}
