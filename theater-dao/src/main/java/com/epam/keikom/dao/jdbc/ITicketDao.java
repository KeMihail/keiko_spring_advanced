package com.epam.keikom.dao.jdbc;


import com.epam.keikom.dao.domain.Ticket;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * this interface create case of additional logic in the domain ticket
 */
public interface ITicketDao extends IAbstractDomainObjectDao<Ticket> {

    public @Nonnull
    Collection<LocalDateTime> getDateFromEvent(@Nonnull Long eventId);

    public @Nonnull
    Collection<Ticket> getTicketFromUser(@Nonnull Long userId);
}
