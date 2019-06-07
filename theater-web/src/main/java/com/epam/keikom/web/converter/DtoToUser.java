package com.epam.keikom.web.converter;

import com.epam.keikom.dao.domain.User;
import com.epam.keikom.web.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.Function;

@Component
public class DtoToUser implements Function<UserDTO, User> {

	@Override
	public User apply(UserDTO dto) {

		final User user = new User();
		user.setId(dto.getId());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setBirthday(dto.getBirthday());

		return user;
	}
}
