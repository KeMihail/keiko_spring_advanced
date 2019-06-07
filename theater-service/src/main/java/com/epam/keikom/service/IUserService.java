package com.epam.keikom.service;

import com.epam.keikom.dao.domain.Ticket;
import com.epam.keikom.dao.domain.User;
import com.epam.keikom.dao.exceptions.UnknownIdentifierException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * @author Yuriy_Tkach
 */
public interface IUserService extends IAbstractDomainObjectService<User> {

    /**
     * Finding user by email
     * 
     * @param email
     *            Email of the user
     * @return found user or <code>null</code>
     */
    public @Nullable User getUserByEmail(@Nonnull String email) throws UnknownIdentifierException;

    public @Nullable
    Collection<Ticket> getTicketFromByUser(@Nonnull Long userId);
}
