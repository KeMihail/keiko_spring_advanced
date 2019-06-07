package com.epam.keikom.service.impl;

import com.epam.keikom.dao.domain.Ticket;
import com.epam.keikom.dao.domain.User;
import com.epam.keikom.dao.exceptions.UnknownIdentifierException;
import com.epam.keikom.dao.jdbc.ITicketDao;
import com.epam.keikom.dao.jdbc.IUserDao;
import com.epam.keikom.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Collection;

@Service
public class UserService implements IUserService
{
	@Resource(name = "userDao")
	IUserDao userDao;

	@Autowired
	private ITicketDao ticketDao;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Nullable
	@Override
	public User getUserByEmail(@Nonnull final String email) throws UnknownIdentifierException
	{
		final User user = userDao.getUserByEmail(email);
		if (user == null){
			throw new UnknownIdentifierException("User with email: " + email +" not found!");
		}
		return user;
	}

	@Nullable
	@Override
	public  Collection<Ticket> getTicketFromByUser(@Nonnull Long userId) {
		return ticketDao.getTicketFromUser(userId);
	}

	@Override
	public User save(@Nonnull final User user)
	{
		if (user.getId() != null){
			userDao.update(user);
		}else {
			user.setId(userDao.save(user).getId());
		}
		return user;
	}

	@Override
	public void remove(@Nonnull final User object)
	{
		userDao.remove(object);
	}

	@Override
	public User getById(@Nonnull final Long id) throws UnknownIdentifierException
	{
		final User user = userDao.getById(id);
		if (user == null){
			throw new UnknownIdentifierException("User with id: " + id +" not found!");
		}

		return user;
	}

	@Nonnull
	@Override
	public Collection<User> getAll()
	{
		return userDao.getAll();
	}
}
