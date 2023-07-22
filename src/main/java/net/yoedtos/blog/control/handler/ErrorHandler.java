package net.yoedtos.blog.control.handler;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import net.yoedtos.blog.control.AbstractBean;

@ManagedBean(name="error")
@RequestScoped
public class ErrorHandler extends AbstractBean {
	private Map<String, Object> requestMap;
	
	public ErrorHandler() {
		this.requestMap = getExContext().getRequestMap();
	}
	
	public String getStatusCode() {
		return requestMap.get("javax.servlet.error.status_code").toString();
	}

	public String getMessage() {
		return (String) requestMap.get("javax.servlet.error.message");
	}

	public String getExceptionType() {
		return requestMap.get("javax.servlet.error.exception_type").toString();
	}

	public String getException() {
		return requestMap.get("javax.servlet.error.exception").getClass().getName();
	}

	public String getRequestURI() {
		return (String) requestMap.get("javax.servlet.error.request_uri");
	}

	public String getServletName() {
		return (String) requestMap.get("javax.servlet.error.servlet_name");
	}
}
