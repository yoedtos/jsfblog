package net.yoedtos.blog.control;

class Constants {
	private Constants() {}

	public static final String LOGIN_REDIRECT = "login?faces-redirect=true";
	public static final String CATEGORY_REDIRECT = "/admin/category?faces-redirect=true";
	public static final String HOME_REDIRECT = "/home/home?faces-redirect=true";
	public static final String INDEX_REDIRECT = "/index?faces-redirect=true";
	
	public static final String SESSION_KEY_USER = "username";
	public static final String SESSION_KEY_ROLE = "role";
	public static final String CATEGORY_ID = "category_id";
}
