package com.epam.keikom.service.aspect;


import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.dao.domain.aspect.CounterAspectDomain;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface ICounterAspectService {

    public void save(@Nonnull Event event);

    public void update(@Nonnull CounterAspectDomain counterAspect);

    public CounterAspectDomain getByEvent(@Nonnull Event event);

    public Collection<CounterAspectDomain> getAll();

    public void remove (@Nonnull CounterAspectDomain counterAspect);
}
