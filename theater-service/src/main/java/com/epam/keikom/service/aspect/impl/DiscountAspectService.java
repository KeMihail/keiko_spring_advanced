package com.epam.keikom.service.aspect.impl;

import com.epam.keikom.dao.domain.aspect.DiscountAspectDomain;
import com.epam.keikom.dao.jdbc.impl.aspect.IDiscountAspectDao;
import com.epam.keikom.service.aspect.IDiscountAspectService;
import org.springframework.stereotype.Service;
import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collection;

@Service
public class DiscountAspectService implements IDiscountAspectService {

    @Resource(name = "discountAspectDao")
    private IDiscountAspectDao aspectDao;

    @Override
    public void save(@Nonnull DiscountAspectDomain aspectDomain) {
        aspectDao.save(aspectDomain);
    }

    @Override
    public void update(@Nonnull DiscountAspectDomain aspectDomain) {
        aspectDao.update(aspectDomain);
    }

    @Override
    public DiscountAspectDomain getByDiscount(@Nonnull String discount) {
        return aspectDao.getByDiscount(discount);
    }

    @Override
    public Collection<DiscountAspectDomain> getAll() {
        return aspectDao.getAll();
    }

    @Override
    public void remove(@Nonnull DiscountAspectDomain counterAspect) {
        aspectDao.remove(counterAspect);
    }
}
