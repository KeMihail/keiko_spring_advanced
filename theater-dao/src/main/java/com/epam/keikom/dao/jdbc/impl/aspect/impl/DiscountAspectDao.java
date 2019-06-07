package  com.epam.keikom.dao.jdbc.impl.aspect.impl;

import com.epam.keikom.dao.domain.aspect.DiscountAspectDomain;
import com.epam.keikom.dao.jdbc.impl.aspect.IDiscountAspectDao;
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
public class DiscountAspectDao implements IDiscountAspectDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = LogManager.getLogger(DiscountAspectDao.class);

    @Override
    public void save(@Nonnull DiscountAspectDomain aspectDomain) {

        final String query = "insert into discountaspect (discount,amount) values(?,?)";
        jdbcTemplate.update(query, new Object[]{aspectDomain.getDiscount(), aspectDomain.getAmount()});
    }

    @Override
    public void update(@Nonnull DiscountAspectDomain aspectDomain) {
        final String query = "update discountaspect set amount = ? where discount = ?";
        jdbcTemplate.update(query, new Object[]{aspectDomain.getAmount(), aspectDomain.getDiscount()});

    }

    @Override
    public DiscountAspectDomain getByDiscount(@Nonnull String discount) {
        final String query = "select * from discountaspect where discount = ?";

        DiscountAspectDomain aspectDomain = null;

        try {
            aspectDomain = jdbcTemplate.queryForObject(query, new Object[]{discount}, new RowMapper<DiscountAspectDomain>() {

                @Override
                public DiscountAspectDomain mapRow(ResultSet resultSet, int i) throws SQLException {

                    return getDiscountAspectDomain(resultSet);
                }
            });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
        }
        return aspectDomain;
    }

    @Override
    public Collection<DiscountAspectDomain> getAll() {
        final String query = "select * from discountaspect";

        Collection<DiscountAspectDomain> aspectDomains = Collections.emptyList();

        try{
            aspectDomains = jdbcTemplate.query(query, new RowMapper<DiscountAspectDomain>() {

                @Override
                public DiscountAspectDomain mapRow(ResultSet resultSet, int i) throws SQLException {

                    return getDiscountAspectDomain(resultSet);

                }
            });
        }catch(EmptyResultDataAccessException e){
            LOGGER.error(e.getMessage());
        }
        return aspectDomains;
    }

    @Override
    public void remove(@Nonnull DiscountAspectDomain counterAspect) {

        final String query = "delete from discountaspect where discount = ?";
        jdbcTemplate.update(query,new Object[]{counterAspect.getDiscount()});

    }

    private DiscountAspectDomain getDiscountAspectDomain(final ResultSet resultSet) throws SQLException {

        DiscountAspectDomain aspectDomain = new DiscountAspectDomain();
        aspectDomain.setDiscount(resultSet.getString("discount"));
        aspectDomain.setAmount(resultSet.getDouble("amount "));

        return aspectDomain;
    }
}
