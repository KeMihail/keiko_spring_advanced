package com.epam.keikom.dao.jdbc.impl.aspect;

import com.epam.keikom.dao.domain.aspect.DiscountAspectDomain;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface IDiscountAspectDao {

    public void save(@Nonnull DiscountAspectDomain aspectDomain);

    public void update(@Nonnull DiscountAspectDomain aspectDomain);

    public DiscountAspectDomain getByDiscount(@Nonnull String discount);

    public Collection<DiscountAspectDomain> getAll();

    public void remove (@Nonnull DiscountAspectDomain counterAspect);
}
