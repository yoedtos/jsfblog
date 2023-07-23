package net.yoedtos.blog.control.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import net.yoedtos.blog.view.AlertMsg;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {

	private static final int PASSWORD_MIN = 4;
	private static final int PASSWORD_MAX = 10;

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {

		String password = (String) value;
		String confirm = (String) comp.getAttributes().get("confirm");

		if (password.length() < PASSWORD_MIN || password.length() > PASSWORD_MAX) {
			throw new ValidatorException(
					AlertMsg.getInstance().setMessage(FacesMessage.SEVERITY_ERROR, "msg.pass_size"));
		}

		if (!(confirm == null || password.equals(confirm))) {
			throw new ValidatorException(
					AlertMsg.getInstance().setMessage(FacesMessage.SEVERITY_ERROR, "msg.pass_mism"));
		}
	}
}
