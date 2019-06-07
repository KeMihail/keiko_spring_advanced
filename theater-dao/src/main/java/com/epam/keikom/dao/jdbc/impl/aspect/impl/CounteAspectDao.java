package  com.epam.keikom.dao.jdbc.impl.aspect.impl;

import com.epam.keikom.dao.domain.Event;
import com.epam.keikom.dao.domain.aspect.CounterAspectDomain;
import com.epam.keikom.dao.jdbc.impl.aspect.ICounterAspectDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

@Repository
public class CounteAspectDao implements ICounterAspectDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = LogManager.getLogger(CounteAspectDao.class);

    @Override
    public void save(@Nonnull Event event) {

        final String query = "insert into counteraspect(event) values(?)";
        jdbcTemplate.update(query, new Object[]{event.getId()});

    }

    @Override
    public void update(@Nonnull CounterAspectDomain counterAspect) {

        final String query = "update counteraspect set byName = ?,byPrice = ?,byBooked = ? where event = ?";
        jdbcTemplate.update(query, new Object[]{counterAspect.getByName(), counterAspect.getByPrice(), counterAspect.getByBooked(), counterAspect.getEvent()});
    }

    @Override
    public CounterAspectDomain getByEvent(@Nonnull Event event) {

        final String query = "select * from counteraspect where event = ?";

        CounterAspectDomain counterAspect = null;

        try{
            counterAspect = jdbcTemplate.queryForObject(query, new Object[]{event.getId()}, new RowMapper<CounterAspectDomain>() {

                @Override
                public CounterAspectDomain mapRow(ResultSet resultSet, int i) throws SQLException {

                    return getCounterAspect(resultSet);
                }
            });
        }catch (EmptyResultDataAccessException e){
            LOGGER.error(e.getMessage());
        }

        return counterAspect;
    }

    @Override
    public Collection<CounterAspectDomain> getAll() {

        final String query = "select * from counteraspect";

        Collection<CounterAspectDomain> aspects = Collections.emptyList();

        try{
            aspects = jdbcTemplate.query(query, new RowMapper<CounterAspectDomain>() {

                @Override
                public CounterAspectDomain mapRow(ResultSet resultSet, int i) throws SQLException {

                    return getCounterAspect(resultSet);

                }
            });
        }catch (EmptyResultDataAccessException e){
            LOGGER.error(e.getMessage());
        }

        return aspects;
    }

    @Override
    public void remove(@Nonnull CounterAspectDomain counterAspect) {

        final String query = "delete from counteraspect where event = ?";
        jdbcTemplate.update(query,new Object[]{counterAspect.getEvent()});
    }

    private CounterAspectDomain getCounterAspect(final ResultSet resultSet) throws SQLException{

        CounterAspectDomain counterAspect = new CounterAspectDomain();
        counterAspect.setEvent(resultSet.getLong("event"));
        counterAspect.setByName(resultSet.getInt("byName"));
        counterAspect.setByPrice(resultSet.getInt("byPrice"));
        counterAspect.setByBooked(resultSet.getInt("byBooked"));

        return counterAspect;
    }
}
