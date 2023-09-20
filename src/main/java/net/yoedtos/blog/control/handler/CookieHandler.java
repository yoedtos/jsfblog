package net.yoedtos.blog.control.handler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHandler {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public CookieHandler(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public Cookie createCookie(String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		
		response.addCookie(cookie);
		return cookie;
	}
	
	public void removeCookie(String name) {
		createCookie(name, "", 0);
	}
	
	public String getCookieValue(String name) {
		String value = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie:cookies) {
				if(name.equals(cookie.getName())) {
					value = cookie.getValue();
				}
			}
		}
		return value;
	}
}
