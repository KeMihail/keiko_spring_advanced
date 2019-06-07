package com.epam.keikom.dao.jdbc;


import com.epam.keikom.dao.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Collection;


public interface IEventDao extends IAbstractDomainObjectDao<Event>
{
	/**
	 * Finding event by name
	 *
	 * @param name
	 * 		Name of the event
	 * @return found event or <code>null</code>
	 */
	public @Nullable
	Collection<Event> getByName(@Nonnull String name);

	/**
	 * Finding events by date range
	 * @param from
	 * 		Date from
	 * @param to
	 * 		Date to
	 * @return returns events for specified date range
	 */
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
}
