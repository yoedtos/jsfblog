package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.HOME;
import static net.yoedtos.blog.control.Constants.HOME_REDIRECT;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.service.UserService;

@ManagedBean(name="profile")
@ViewScoped
public class ProfileBean extends AbstractBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileBean.class);
	
	private UserDTO dto;
	private UserService userService;
	
	@ManagedProperty(value="#{auth}")
	private AuthBean authBean;
	
	public ProfileBean() {
		super();
		userService = ServiceFactory.create(UserService.class);
	}
	
	public void loadUser() {
		dto = authBean.getUser();
	}

	public String update() throws ServiceException {	
		try {
			userService.update(dto);
		} catch (ServiceException e) {
			showErrorMessage(false);
			return "";
		}

		showSuccessMessage(true);
		return HOME_REDIRECT;
	}
	
	public void cancel() {
		try {
			redirect(HOME);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
		}
	}
	
	public UserDTO getDto() {
		return dto;
	}

	public void setDto(UserDTO dto) {
		this.dto = dto;
	}
	
	public void setAuthBean(AuthBean authBean) {
		this.authBean = authBean;
	}
}
