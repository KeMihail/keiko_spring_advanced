package com.epam.keikom.dao.config;

import com.epam.keikom.dao.domain.Auditorium;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@ComponentScan(basePackages = "com.epam.keikom.dao")
public class DaoSpringConfig {

	@Value("${red_name}")
	private String RED_NAME;
	@Value("${red_numberOfSeats}")
	private Long RED_NUMBER_OF_SEATS;
	@Resource(name = "vipSeatsRed")
	private Set<Long> RED_VIP_SEATS;

	@Value("${green_name}")
	private String GREEN_NAME;
	@Value("${green_numberOfSeats}")
	private Long GREEN_NUMBER_OF_SEATS;
	@Resource(name = "vipSeatsGreen")
	private Set<Long> GREEN_VIP_SEATS;

	@Value("${black_name}")
	private String BLACK_NAME;
	@Value("${black_numberOfSeats}")
	private Long BLACK_NUMBER_OF_SEATS;
	@Resource(name = "vipSeatsBlack")
	private Set<Long> BLACK_VIP_SEATS;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {

		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertySourcesPlaceholderConfigurer.setLocation(new ClassPathResource("auditoriums.properties"));
		propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);

		return propertySourcesPlaceholderConfigurer;
	}

	@Bean
	public JdbcTemplate getJdbcTemplate() {
		final JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(getDataSource());

		return jdbcTemplate;
	}

	@Bean
	public DataSource getDataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(
				"jdbc:mysql://localhost:3306/springcore?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		dataSource.setUsername("root");
		dataSource.setPassword("500290");
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

		return dataSource;
	}

	@Bean
	public Set<Long> vipSeatsRed() {
		final Set<Long> vipSeats = new HashSet<>();
		vipSeats.add(Long.valueOf(1));
		vipSeats.add(Long.valueOf(2));
		vipSeats.add(Long.valueOf(3));

		return vipSeats;
	}

	@Bean
	public Set<Long> vipSeatsGreen() {
		final Set<Long> vipSeats = new HashSet<>();
		vipSeats.add(Long.valueOf(1));
		vipSeats.add(Long.valueOf(2));
		vipSeats.add(Long.valueOf(3));
		vipSeats.add(Long.valueOf(4));
		vipSeats.add(Long.valueOf(5));

		return vipSeats;
	}

	@Bean
	public Set<Long> vipSeatsBlack() {
		final Set<Long> vipSeats = new HashSet<>();
		vipSeats.add(Long.valueOf(1));
		vipSeats.add(Long.valueOf(2));
		vipSeats.add(Long.valueOf(3));
		vipSeats.add(Long.valueOf(4));
		vipSeats.add(Long.valueOf(5));
		vipSeats.add(Long.valueOf(6));

		return vipSeats;
	}

	@Bean
	public Auditorium redAuditorium() {
		Auditorium auditorium = new Auditorium();
		auditorium.setName(RED_NAME);
		auditorium.setNumberOfSeats(RED_NUMBER_OF_SEATS);
		auditorium.setVipSeats(RED_VIP_SEATS);

		return auditorium;
	}

	@Bean
	public Auditorium greenAuditorium() {
		Auditorium auditorium = new Auditorium();
		auditorium.setName(GREEN_NAME);
		auditorium.setNumberOfSeats(GREEN_NUMBER_OF_SEATS);
		auditorium.setVipSeats(GREEN_VIP_SEATS);

		return auditorium;
	}

	@Bean
	public Auditorium blackAuditorium() {
		Auditorium auditorium = new Auditorium();
		auditorium.setName(BLACK_NAME);
		auditorium.setNumberOfSeats(BLACK_NUMBER_OF_SEATS);
		auditorium.setVipSeats(BLACK_VIP_SEATS);

		return auditorium;
	}

	@Bean
	public Map<String, Auditorium> auditoriumsMap() {
		final Map<String, Auditorium> auditoriums = new HashMap<>();
		auditoriums.put("Red", redAuditorium());
		auditoriums.put("Green", greenAuditorium());
		auditoriums.put("Black", blackAuditorium());

		return auditoriums;
	}
}
