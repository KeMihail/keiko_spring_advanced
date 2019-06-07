package com.epam.keikom.dao.jdbc.impl;

import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.dao.domain.EventRating;
import com.epam.keikom.dao.jdbc.IEventDao;
import com.epam.keikom.dao.jdbc.ITicketDao;
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
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Repository
public class EventDao implements IEventDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "ticketDao")
    private ITicketDao ticketDao;

    private static final Logger LOGGER = LogManager.getLogger(EventDao.class);

    @Nullable
    @Override
    public Collection<Event> getByName(@Nonnull final String name) {

        final String query = "select * from event where name = ?";
        List<Event> events = Collections.emptyList();

        try {
            events = jdbcTemplate.query(query, new Object[]{name}, new RowMapper<Event>() {

                @Override
                public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                    return getEvent(resultSet);
                }
            });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
        }
        return events;
    }

    @Nonnull
    @Override
    public Collection<Event> getForDateRange(@Nonnull final LocalDateTime from, @Nonnull final LocalDateTime to) {
        List<Event> result = new ArrayList<>();
        final List<Event> events = new ArrayList<>(getAll());

        for (final Event event : events) {

            final NavigableSet<LocalDateTime> dateTimes = new TreeSet<>(ticketDao.getDateFromEvent(event.getId()));
            event.setAirDates(dateTimes);

            for (final LocalDateTime date : event.getAirDates()) {
                if (date.isAfter(from.minusMinutes(1)) && date.isBefore(to.plusMinutes(1))) {
                    result.add(event);
                }
            }
        }
        return result;
    }

    @Nonnull
    @Override
    public Collection<Event> getNextEvents(@Nonnull final LocalDateTime to) {
        List<Event> result = new ArrayList<>();
        final List<Event> events = new ArrayList<>(getAll());

        for (final Event event : events) {

            final NavigableSet<LocalDateTime> dateTimes = new TreeSet<>(ticketDao.getDateFromEvent(event.getId()));
            event.setAirDates(dateTimes);

            //because tests can run at different times:
            final LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(8,0,0));

            for (final LocalDateTime date : event.getAirDates()) {
                if (date.isAfter(dateTime) && date.isBefore(to.plusMinutes(1))) {
                    result.add(event);
                }
            }
        }
        return result;
    }

    @Override
    public Event save(@Nonnull final Event event) {

        final String query = "insert into event (name,basePrice,rating) values(?,?,?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(query, new String[]{"id"});
                        ps.setString(1,event.getName());
                        ps.setDouble(2,event.getBasePrice());
                        ps.setString(3,event.getRating().toString());
                        return ps;
                    }
                },
                keyHolder);
        event.setId(Long.valueOf(keyHolder.getKey().intValue()));

        return event;
    }

    @Override
    public void remove(@Nonnull final Event event) {

        final String query = "delete from event where id = ?";
        jdbcTemplate.update(query, new Object[]{event.getId()});
    }

    @Override
    public Event getById(@Nonnull final Long id) {

        final String query = "select * from event where id = ?";
        Event event = null;

        try {
            event = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<Event>() {

                @Override
                public Event mapRow(ResultSet resultSet, int i) throws SQLException {

                    return getEvent(resultSet);
                }
            });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
        }
        return event;
    }

    @Nonnull
    @Override
    public Collection getAll() {

        final String query = "select * from event";
        List<Event> events = Collections.emptyList();

        try {
            events = jdbcTemplate.query(query, new RowMapper<Event>() {

                @Override
                public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                    return getEvent(resultSet);
                }
            });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
        }
        return events;
    }

    @Override
    public Event update(@Nonnull Event event) {

        final String query = "update event set name = ?,basePrice = ?,rating = ? where id = ?";
        jdbcTemplate.update(query, new Object[]{event.getName(), event.getBasePrice(), event.getRating().toString(), event.getId()});
        return event;
    }

    private Event getEvent(final ResultSet resultSet) throws SQLException {

        final Event event = new Event();
        event.setId(resultSet.getLong("id"));
        event.setRating(EventRating.valueOf(resultSet.getString("rating")));
        event.setName(resultSet.getString("name"));
        event.setBasePrice(resultSet.getDouble("basePrice"));

        return event;
    }
}
