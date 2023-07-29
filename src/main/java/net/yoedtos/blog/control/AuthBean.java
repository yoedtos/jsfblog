package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.HOME_REDIRECT;
import static net.yoedtos.blog.control.Constants.INDEX_REDIRECT;
import static net.yoedtos.blog.control.Constants.SESSION_KEY_ROLE;
import static net.yoedtos.blog.control.Constants.SESSION_KEY_USER;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.yoedtos.blog.exception.AuthException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.dto.Login;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.service.AuthService;

@ManagedBean(name="auth")
@SessionScoped
public class AuthBean extends AbstractBean{
	
	private UserDTO user;
	private Login dto;
	
	public AuthBean() {
		this.setDto(new Login());
	}
	
	public String login() {
		AuthService authService = ServiceFactory.create(AuthService.class);
		try {
			user = authService.login(dto);
			setSessionAttribute(SESSION_KEY_USER, user.getUsername());
			setSessionAttribute(SESSION_KEY_ROLE, user.getRole().getValue());
		} catch (ServiceException | AuthException e) {
			showErrorMessage(true);
			return "";
		}
	
		return HOME_REDIRECT;
	}
	
	public String logout() {
		invalidateSession();
		
		return INDEX_REDIRECT;				
	}

	public Role[] getRoleValues() {
		return Role.values();
	}
	
	public UserDTO getUser() {
		return user;
	}

	public Login getDto() {
		return dto;
	}

	public void setDto(Login dto) {
		this.dto = dto;
	}
}	

