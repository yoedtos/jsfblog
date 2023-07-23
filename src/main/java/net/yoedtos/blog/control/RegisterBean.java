package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.LOGIN_REDIRECT;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.service.UserService;

@ManagedBean(name="register")
@ViewScoped
public class RegisterBean extends AbstractBean {
	
	private UserDTO dto;
	
	public RegisterBean() {
		this.dto = new UserDTO.Builder(null).hostAddress(getRemoteAddress()).build();
	}
	
	public String submit() {
		UserService userService = ServiceFactory.create(UserService.class);
		try {
			userService.create(dto);
		} catch (Exception e) {
			showErrorMessage(false);
			return null;
		}

		showSuccessMessage(true);
		return LOGIN_REDIRECT;
	}
	
	public UserDTO getDto() {
		return dto;
	}

	public void setDto(UserDTO dto) {
		this.dto = dto;
	}
}
