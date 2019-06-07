package com.epam.keikom.dao.jdbc;


import com.epam.keikom.dao.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public interface IUserDao extends IAbstractDomainObjectDao<User>
{

	/**
	 * Finding user by email
	 *
	 * @param email
	 * 		Email of the user
	 * @return found user or <code>null</code>
	 */
	public @Nullable
	User getUserByEmail(@Nonnull String email);

}
