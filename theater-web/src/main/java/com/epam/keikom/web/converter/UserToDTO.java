package com.epam.keikom.web.converter;

import com.epam.keikom.dao.domain.User;
import com.epam.keikom.web.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserToDTO implements Function<User, UserDTO> {

	@Override
	public UserDTO apply(User user) {

		final UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setBirthday(user.getBirthday());

		return dto;
	}
}
