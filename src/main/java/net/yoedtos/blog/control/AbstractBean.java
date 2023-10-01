package net.yoedtos.blog.control;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.yoedtos.blog.view.AlertMsg;

public abstract class AbstractBean {

	protected String getRemoteAddress() {
		HttpServletRequest request = getHttpRequest();
		return request.getRemoteHost();
	}
	
	protected String getURLWithContextPath() {
		HttpServletRequest request = getHttpRequest();
		 return request.getScheme() + 
				"://" + request.getServerName() + 
				":" + request.getServerPort() + request.getContextPath();
	}
	
	protected ExternalContext getExContext() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return fc.getExternalContext();
	}
	
	protected void forward(String page) throws IOException {
		getExContext().dispatch(page);
	}
	
	protected void redirect(String page) throws IOException {
		getExContext().redirect(page);
	}
	
	protected void showErrorMessage(boolean flash) {
		AlertMsg.getInstance()
			.setMessageWithFlash(FacesMessage.SEVERITY_ERROR, "msg.failed", flash);
	}
	
	protected void showSuccessMessage(boolean flash) {
		AlertMsg.getInstance()
			.setMessageWithFlash(FacesMessage.SEVERITY_INFO, "msg.success", flash);
	}

	protected void responseComplete() {
		FacesContext.getCurrentInstance().responseComplete();
	}

	protected void invalidateSession() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	
	protected void setSessionAttribute(String name, Object atributte) {
		HttpSession session = (HttpSession) getExContext().getSession(false);
		session.setAttribute(name, atributte);
	}
	
	protected Object getSessionAttribute(String name) {
		HttpSession session = (HttpSession) getExContext().getSession(false);
		return session.getAttribute(name);
	}

	protected void removeSessionAttribute(String name) {
		HttpSession session = (HttpSession) getExContext().getSession(false);
		session.removeAttribute(name);
	}

	protected String getRequestParam(String param) {
		HttpServletRequest request = getHttpRequest();
		return request.getParameter(param);
	}

	protected HttpServletRequest getHttpRequest() {
		return (HttpServletRequest) getExContext().getRequest();
	}
	
	protected HttpServletResponse getHttpResponse() {
		return (HttpServletResponse) getExContext().getResponse();
	}
}

