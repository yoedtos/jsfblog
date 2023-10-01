package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.HOME_REDIRECT;
import static net.yoedtos.blog.control.Constants.INDEX_REDIRECT;
import static net.yoedtos.blog.control.Session.BADGE;
import static net.yoedtos.blog.control.Session.INDEX;
import static net.yoedtos.blog.control.Session.MAX_AGE;
import static net.yoedtos.blog.control.Session.SESSION_GUEST;
import static net.yoedtos.blog.control.Session.SESSION_KEY_ROLE;
import static net.yoedtos.blog.control.Session.SESSION_KEY_USER;
import static net.yoedtos.blog.control.Session.SESSION_USER;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.control.handler.CookieHandler;
import net.yoedtos.blog.exception.AuthException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.dto.Login;
import net.yoedtos.blog.model.dto.RememberData;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.service.AuthService;
import net.yoedtos.blog.service.RememberService;

@ManagedBean(name="auth")
@SessionScoped
public class AuthBean extends AbstractBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthBean.class);
	
	private UserDTO user;
	private Login dto;
	private CookieHandler cookieHandler;
	private RememberService rememberService;
	
	public AuthBean() {
		this.setDto(new Login());
		rememberService = ServiceFactory.create(RememberService.class); 
	}
	
	@PostConstruct
	public void init() {
		user = (UserDTO) getSessionAttribute(SESSION_USER);
		if(user == null)
			getHttpRequest().getSession().setAttribute(SESSION_GUEST, "guest");		
	}
	
	public String login() {
		AuthService authService = ServiceFactory.create(AuthService.class);
		
		try {
			user = authService.login(dto);
			createUserSession(user);
			if(dto.isRemember()) {
				RememberData remember = rememberService.set(user.getUsername());
				createCookies(remember);
			}
		} catch (ServiceException | AuthException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(true);
			return "";
		}
	
		return HOME_REDIRECT;
	}
	
	public String logout() {
		try {
			cookieHandler = new CookieHandler(getHttpRequest(), getHttpResponse());
			if (cookieHandler.getCookieValue(BADGE) != null) {
				rememberService.unset((String) getSessionAttribute(SESSION_KEY_USER));
				cookieHandler.removeCookie(INDEX).removeCookie(BADGE);
			}
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(true);
			return "";
		}
		invalidateSession();

		return INDEX_REDIRECT;
	}

	private void createUserSession(UserDTO user) {
		setSessionAttribute(SESSION_KEY_USER, user.getUsername());
		setSessionAttribute(SESSION_KEY_ROLE, user.getRole().getValue());
		setSessionAttribute(SESSION_GUEST, null);
	}
	
	private void createCookies(RememberData data) {
		cookieHandler = new CookieHandler(getHttpRequest(), getHttpResponse());
		cookieHandler.createCookie(INDEX, data.getIndex(), MAX_AGE)
					 .createCookie(BADGE, data.getBadge(), MAX_AGE);
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

