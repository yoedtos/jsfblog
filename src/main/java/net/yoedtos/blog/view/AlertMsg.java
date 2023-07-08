package net.yoedtos.blog.view;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Show FaceMessage with i18 features
 * @author yoedtos
 *
 */
public class AlertMsg {

	private static AlertMsg instance;
	private ResourceBundle msgBundle;
	
	private AlertMsg() {}
	
	public static AlertMsg getInstance() {
		if(instance == null) {
			instance = new AlertMsg();
		}
		return instance;
	}
	
	/**
	 * Set located messages to show on UIview 
	 * @param severity message severity
	 * @param key	   string to get message value 	
	 * @param flash    enable flash attribute on redirection
	 */
	public void setMessageWithFlash(FacesMessage.Severity severity, String key, boolean flash) {
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		msgBundle = ResourceBundle.getBundle("i18n.text", locale);
		FacesMessage message = new FacesMessage(msgBundle.getString(key));
		message.setSeverity(severity);
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(flash);
		context.addMessage("growlMsg", message);
	}
	
	public FacesMessage setMessage(FacesMessage.Severity severity, String key) {
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		msgBundle = ResourceBundle.getBundle("i18n.text", locale);
		FacesMessage message = new FacesMessage(msgBundle.getString(key));
		message.setSeverity(severity);
		return message;
	}
}
