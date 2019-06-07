package com.epam.keikom.service.aspect.impl;

import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.dao.domain.aspect.CounterAspectDomain;
import com.epam.keikom.dao.jdbc.impl.aspect.ICounterAspectDao;
import com.epam.keikom.service.aspect.ICounterAspectService;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collection;

@Service
public class CounterAspectService implements ICounterAspectService {

    @Resource(name = "counteAspectDao")
    private ICounterAspectDao aspectDao;

    @Override
    public void save(@Nonnull Event event) {
        aspectDao.save(event);
    }

    @Override
    public void update(@Nonnull CounterAspectDomain counterAspect) {
        aspectDao.update(counterAspect);
    }

    @Override
    public CounterAspectDomain getByEvent(@Nonnull Event event) {
        return aspectDao.getByEvent(event);
    }

    @Override
    public Collection<CounterAspectDomain> getAll() {
        return aspectDao.getAll();
    }

    @Override
    public void remove(@Nonnull CounterAspectDomain counterAspect) {
        aspectDao.remove(counterAspect);
    }
}
