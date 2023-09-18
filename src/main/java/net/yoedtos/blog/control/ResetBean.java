package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.LOGIN_REDIRECT;
import static net.yoedtos.blog.util.Constants.TOKEN_SIZE;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.dto.Login;
import net.yoedtos.blog.service.ResetService;

@ManagedBean(name="reset")
@ViewScoped
public class ResetBean extends AbstractBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResetBean.class);
	
	private String token;
	private Login dto;
	private ResetService resetService;
		
	public ResetBean() {
		super();
		resetService = ServiceFactory.create(ResetService.class);
		dto = new Login();
	}
	
	public String initiate() {
	 	try {
			resetService.initiate(dto.getUsername());
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
			return null;
		}
		showSuccessMessage(true);
		return LOGIN_REDIRECT;
	}

	public String conclude() {
		try {
			if(token == null || token.length() != TOKEN_SIZE) 
				throw new ServiceException("Invalid token");
			resetService.complete(dto, token);
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
			return null;
		}

		showSuccessMessage(true);
		return LOGIN_REDIRECT;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public Login getDto() {
		return dto;
	}
	
	public void setDto(Login dto) {
		this.dto = dto;
	}
}	
