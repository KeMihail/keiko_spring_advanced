package com.epam.keikom.dao.jdbc.impl;

import com.epam.keikom.dao.domain.Ticket;
import com.epam.keikom.dao.jdbc.IEventDao;
import com.epam.keikom.dao.jdbc.ITicketDao;
import com.epam.keikom.dao.jdbc.IUserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
public class TicketDao implements ITicketDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "eventDao")
	private IEventDao eventDao;

	private static final Logger LOGGER = LogManager.getLogger(TicketDao.class);

	@Override
	public Ticket update(@Nonnull Ticket ticket) {

		final String query = "update ticket set dateTime = ?,seat = ?, user = ?, event = ? where id = ?";
		final Long userId = (ticket.getUser() == null) ? null : ticket.getUser().getId();
		jdbcTemplate.update(query, new Object[] { ticket.getDateTime(), ticket.getSeat(), userId,
				ticket.getEvent().getId(), ticket.getId() });

		return ticket;
	}

	@Override
	public Ticket save(@Nonnull Ticket ticket) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		final String query = "insert into ticket (dateTime,seat,event) values (?,?,?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query, new String[] { "id" });
				Timestamp timestamp = Timestamp.valueOf(ticket.getDateTime());
				ps.setTimestamp(1, timestamp);
				ps.setLong(2, ticket.getSeat());
				ps.setInt(3, ticket.getEvent().getId().intValue());

				return ps;
			}
		}, keyHolder);
		ticket.setId(Long.valueOf(keyHolder.getKey().intValue()));
		return ticket;
	}

	@Override
	public void remove(@Nonnull Ticket ticket) {

		final String query = "delete from ticket where id = ?";
		jdbcTemplate.update(query, new Object[] { ticket.getId() });
	}

	@Override
	public Ticket getById(@Nonnull Long id) throws EmptyResultDataAccessException {

		final String query = "select * from ticket where id = ?";
		Ticket ticket = null;

		try {
			ticket = jdbcTemplate.queryForObject(query, new Object[] { id }, new RowMapper<Ticket>() {

				@Override
				public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {

					return getTicket(resultSet);
				}
			});
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error(e.getMessage());
		}
		return ticket;
	}

	@Nonnull
	@Override
	public Collection<Ticket> getAll() {

		final String query = "select * from ticket";
		List<Ticket> tickets = Collections.emptyList();

		try {
			tickets = jdbcTemplate.query(query, new RowMapper<Ticket>() {

				@Override
				public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {

					return getTicket(resultSet);
				}
			});
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error(e.getMessage());
		}
		return tickets;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Nonnull
	@Override
	public Collection<LocalDateTime> getDateFromEvent(@Nonnull Long eventId) {

		final String query = "select ticket.dateTime from ticket where ticket.event = ?";

		Collection<LocalDateTime> dateTimes = jdbcTemplate.query(query, new Object[] { eventId },
				new RowMapper<LocalDateTime>() {

					@Override
					public LocalDateTime mapRow(ResultSet resultSet, int i) throws SQLException {

						LocalDateTime dateTime = resultSet.getTimestamp("dateTime").toLocalDateTime();

						return dateTime;
					}
				});

		return dateTimes;
	}

	@Nonnull
	@Override
	public Collection<Ticket> getTicketFromUser(@Nonnull Long userId) {

		final String query = "select * from ticket where ticket.user = ?";

		final Collection<Ticket> tickets = jdbcTemplate.query(query, new Object[] { userId }, new RowMapper<Ticket>() {

			@Override
			public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {

				return getTicket(resultSet);
			}
		});

		return tickets;
	}

	private Ticket getTicket(ResultSet resultSet) throws SQLException {

		final Ticket ticket = new Ticket();
		ticket.setId(resultSet.getLong("id"));
		ticket.setDateTime(resultSet.getTimestamp("dateTime").toLocalDateTime());
		ticket.setSeat(resultSet.getInt("seat"));
		ticket.setUser(userDao.getById(resultSet.getLong("user")));
		ticket.setEvent(eventDao.getById(resultSet.getLong("event")));

		return ticket;
	}
}
