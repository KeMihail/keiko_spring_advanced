package com.epam.keikom.dao.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.epam.keikom.dao.domain.User;
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

@Repository
public class UserDao implements IUserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = LogManager.getLogger(UserDao.class);


    @Nullable
    @Override
    public User getUserByEmail(@Nonnull final String email) {

        User user = null;
        final String query = "select * from user where email = ?";

        try {
            user = jdbcTemplate.queryForObject(query, new Object[]{email}, new RowMapper<User>() {

                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {

                    return getUser(resultSet);
                }
            });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
        }
        return user;
    }

    @Override
    public User save(@Nonnull final User user) {

        final String query = "insert into user (firstName,lastName,email,birthday) values (?,?,?,?)";

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(query, new String[]{"id"});
                        ps.setString(1, user.getFirstName());
                        ps.setString(2, user.getLastName());
                        ps.setString(3, user.getEmail());
                        ps.setDate(4, java.sql.Date.valueOf(user.getBirthday()));
                        return ps;
                    }
                },
                keyHolder);
        user.setId(Long.valueOf(keyHolder.getKey().intValue()));

        return user;
    }

    @Override
    public void remove(@Nonnull final User user) {

        final String query = "delete from user where id = ?";
        jdbcTemplate.update(query, new Object[]{user.getId()});
    }

    @Override
    public User getById(@Nonnull final Long id) {

        final String query = "select * from user where id = ?";
        User user = null;

        try {
            user = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<User>() {

                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    return getUser(resultSet);
                }
            });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
        }

        return user;
    }

    @Nonnull
    @Override
    public Collection getAll() {

        final String query = "select * from user";
        List<User> users = Collections.emptyList();

        try {
            users = jdbcTemplate.query(query, new RowMapper<User>() {

                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {

                    return getUser(resultSet);
                }
            });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
        }
        return users;
    }

    @Override
    public User update(@Nonnull User user) {

        final String query = "update user set firstName = ?,lastName = ?,email = ?,birthday = ? where id = ?";
        jdbcTemplate.update(query, new Object[]{user.getFirstName(), user.getLastName(), user.getEmail(), java.sql.Date.valueOf(user.getBirthday()), user.getId()});

        return user;
    }

    private User getUser(ResultSet resultSet) throws SQLException {

        final User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setEmail(resultSet.getString("email"));

        final LocalDateTime dateTime = resultSet.getTimestamp("birthday").toLocalDateTime();
        user.setBirthday(dateTime.toLocalDate());

        return user;
    }
}

