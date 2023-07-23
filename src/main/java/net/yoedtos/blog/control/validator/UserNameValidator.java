package net.yoedtos.blog.control.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.service.UserService;
import net.yoedtos.blog.view.AlertMsg;

@FacesValidator("userNameValidator")
public class UserNameValidator implements Validator {

	private static final int USERNAME_MIN = 6;
	private static final int USERNAME_MAX = 20;

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {

		String username = value.toString();
		String register = (String) comp.getAttributes().get("register");

		if (username.length() < USERNAME_MIN || username.length() > USERNAME_MAX) {
			throw new ValidatorException(
					AlertMsg.getInstance().setMessage(FacesMessage.SEVERITY_ERROR, "msg.user_size"));
		}

		if (register != null && register.equals("true")) {
			UserService userService = ServiceFactory.create(UserService.class);
			try {
				if (userService.exist(username)) {
					throwAlertMessage();
				}
			} catch (ServiceException e) {
				throwAlertMessage();
			}
		}
	}
	
	private void throwAlertMessage() {
		throw new ValidatorException(
				AlertMsg.getInstance().setMessage(FacesMessage.SEVERITY_ERROR, "msg.user_na"));
	}
}
