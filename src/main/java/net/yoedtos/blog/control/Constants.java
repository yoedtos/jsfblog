package net.yoedtos.blog.control;

class Constants {
	private Constants() {}

	public static final String LOGIN_REDIRECT = "login?faces-redirect=true";
	public static final String CATEGORY_REDIRECT = "/admin/category?faces-redirect=true";
	public static final String HOME = "../home/home.jsf";
	public static final String HOME_REDIRECT = "/home/home?faces-redirect=true";
	public static final String INDEX_REDIRECT = "/index?faces-redirect=true";
	public static final String USERS = "users.jsf";
	public static final String USERS_REDIRECT = "/admin/users?faces-redirect=true";
	public static final String ADMIN = "../admin/home.jsf";
	public static final String ADMIN_REDIRECT = "/admin/home?faces-redirect=true";
	public static final String POST = "post.jsf";
	public static final String POSTS = "posts.jsf";
	public static final String POSTS_REDIRECT = "/home/posts?faces-redirect=true";
	public static final String NOT_FOUND = "404.jsf";
	public static final String PARAM_VIEW_REDIRECT = "view?faces-redirect=true&includeViewParams=true";
	public static final String MEDIAS_REDIRECT = "/home/files?faces-redirect=true";
	public static final String MEDIA = "/media";
	public static final String SEARCH = "search.jsf";
	
	public static final String SESSION_KEY_USER = "username";
	public static final String SESSION_KEY_ROLE = "role";
	
	public static final String CATEGORY_ID = "category_id";
	public static final String USER_ID = "user_id";
	public static final String POST_ID = "post_id";
	public static final String FILE_ID = "file_id";
	public static final String FIRST_PG = "1";
	public static final String POST_PAR_ID = "postId";
	public static final String COMMENT_PAR_ID = "commentId";
}
