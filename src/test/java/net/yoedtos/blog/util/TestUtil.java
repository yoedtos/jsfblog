package net.yoedtos.blog.util;

import static net.yoedtos.blog.util.TestConstants.*;

import java.util.Date;

import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.model.entity.User;

public class TestUtil {

	private TestUtil() {}
	
	public static UserDTO createUserDTO(Date createAt) {
		return new UserDTO.Builder(USERNAME)
				.fullname(FULLNAME)
				.email(EMAIL)
				.password(PASSWORD)
				.hostAddress(HOST_ADDRESS)
				.role(Role.MEMBER)
				.active(true)
				.createdAt(createAt)
				.build();
	}
	
	public static User createUserOne(Date createAt) {
		 return new User.Builder(USERNAME)
				.userId(USER_ID)
				.fullname(FULLNAME)
				.email(EMAIL)
				.hostAddress(HOST_ADDRESS)
				.role(Role.MEMBER)
				.active(true)
				.createAt(createAt)
				.build();
	}
}
