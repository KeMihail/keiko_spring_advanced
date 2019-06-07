package com.epam.keikom.service;

import java.util.Collection;

import javax.annotation.Nonnull;

import com.epam.keikom.dao.domain.DomainObject;
import com.epam.keikom.dao.exceptions.UnknownIdentifierException;

/**
 * @author Yuriy_Tkach
 *
 * @param <T>
 *            DomainObject subclass
 */
public interface IAbstractDomainObjectService<T extends DomainObject> {

    /**
     * Saving new object to storage or updating existing one
     * 
     * @param object
     *            Object to save
     * @return saved object with assigned id
     */
    public T save(@Nonnull T object);

    /**
     * Removing object from storage
     * 
     * @param object
     *            Object to remove
     */
    public void remove(@Nonnull T object);

    /**
     * Getting object by id from storage
     * 
     * @param id
     *            id of the object
     * @return Found object or <code>null</code>
     */
    public T getById(@Nonnull Long id)throws UnknownIdentifierException;

    /**
     * Getting all objects from storage
     * 
     * @return collection of objects
     */
    public @Nonnull Collection<T> getAll();
}
