package net.yoedtos.blog.control.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import net.yoedtos.blog.view.AlertMsg;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator {

	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\." + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*"
			+ "(\\.[A-Za-z]{2,})$";

	private static final int EMAIL_MIN = 10;
	private static final int EMAIL_MAX = 50;

	private Pattern pattern;

	public EmailValidator() {
		pattern = Pattern.compile(EMAIL_REGEX);
	}

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {

		String email = value.toString();

		if (email.length() < EMAIL_MIN || email.length() > EMAIL_MAX) {
			throw new ValidatorException(
					AlertMsg.getInstance().setMessage(FacesMessage.SEVERITY_ERROR, "msg.email_size"));
		}

		Matcher matcher = pattern.matcher(value.toString());

		if (!matcher.matches()) {
			throw new ValidatorException(
					AlertMsg.getInstance().setMessage(FacesMessage.SEVERITY_ERROR, "msg.email_invld"));
		}
	}
}
