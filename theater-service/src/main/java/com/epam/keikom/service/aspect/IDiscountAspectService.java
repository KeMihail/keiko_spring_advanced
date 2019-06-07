package com.epam.keikom.service.aspect;


import com.epam.keikom.dao.domain.aspect.DiscountAspectDomain;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface IDiscountAspectService {

    public void save(@Nonnull DiscountAspectDomain aspectDomain);

    public void update(@Nonnull DiscountAspectDomain aspectDomain);

    public DiscountAspectDomain getByDiscount(@Nonnull String discount);

    public Collection<DiscountAspectDomain> getAll();

    public void remove(@Nonnull DiscountAspectDomain counterAspect);
}
