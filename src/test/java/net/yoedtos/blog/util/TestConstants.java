package net.yoedtos.blog.util;

import net.yoedtos.blog.model.Type;
import net.yoedtos.blog.view.i18n.Language;

public class TestConstants {
	
	private TestConstants() {}
	
	public final static String DATA_SET = "test-data.xml";
	
	public final static long USER_ONE_ID = 1L;
	public final static long USER_TWO_ID = 2L;
	public final static long USER_NEW_ID = 3L;
	public final static String USERNAME_ONE = "userone";
	public final static String FULLNAME_ONE = "User One";
	public final static String EMAIL_ONE = "userone@domain.com";
	public final static String USERNAME_TWO = "usertwo";
	public final static String FULLNAME_TWO = "User Two";
	public final static String EMAIL_TWO = "usertwo@domain.com";
	public final static String USERNAME_NEW = "newuser";
	public final static String FULLNAME_NEW = "New User";
	public final static String EMAIL_NEW = "newuser@domain.com";
	public final static String FULL_UPDATE = "User One Update";
	public final static String EMAIL_UPDATE = "user-one@domain.com";
	public final static String PASS_UPDATE = "password1";

	public final static String CREATE_ONE = "2012-09-01 18:29:33.633";
	public final static String CREATE_TWO = "2012-09-01 20:31:39.944";
	
	public final static String PASSWORD = "password";
	public final static String ENCODED = "drowssap";
	public final static String HOST_ADDRESS= "127.0.0.1";
	
	public final static long CATEGORY_ONE_ID = 1L;
	public final static long CATEGORY_TWO_ID = 2L;
	public final static long CATEGORY_NEW_ID = 3L;
	public final static String CATEGORY_ONE = "Lorem";
	public final static String CATEGORY_TWO = "Nullam";
	public final static String CATEGORY_NEW = "Treienld";
	
	public final static long POST_ONE_ID = 1L;
	public final static long POST_TWO_ID = 2L;
	public final static long POST_NEW_ID = 3L;
	public final static String TITLE_ONE = "Etiam sit amet orci";
	public final static String INTRO_ONE = "Dolor sit amet, consectetuer adipiscing elit. Aenean commodo";
	public final static String CONTENT_ONE = "Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis "
										+ "dis parturient montes, nascetur ridiculus mus. Donec quam felis";
	
	public final static String TITLE_TWO = "Vivamus elementum semper";
	public final static String INTRO_TWO = "Nulla consequat massa quis enim. Donec pede justo";
	public final static String CONTENT_TWO = "Curabitur ullamcorper ultricies nisi. Nam eget dui.";
	
	public final static String TITLE_NEW = "Vestibulum ante ipsum primis";
	public final static String INTRO_NEW = "Curae; In ac dui quis mi consectetuer lacinia";
	public final static String CONTENT_NEW = "Duis arcu tortor, suscipit eget, imperdiet nec, imperdiet iaculis, ipsum.";
	
	public final static String TITLE_UPDATE = "Vivamus elementum semper";
	public final static String INTRO_UPDATE = "Nulla consequat massa quis enim. Donec pede justo";
	public final static String CONTENT_UPDATE = "Curabitur ullamcorper ultricies nisi. Nam eget dui."
								+ "Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus,"
								+ "sem quam semper libero";

	public final static String META_DESC = "Lorem ipsum dolor sit amet, consectetuer";
	public final static String META_KEY = "Lorem, Ipsum";
	
	public final static long COMMENT_ONE_ID = 1L;
	public final static long COMMENT_TWO_ID = 2L;
	public final static long COMMENT_NEW_ID = 3L;
	public final static String AUTHOR_ONE = "Visitor One";
	public final static String AUTHOR_TWO = "Visitor Two";
	public final static String AUTHOR_NEW = "Visitor New";
	public final static String COMMENT_ONE = "Nulla consequat massa quis enim. Donec pede justo,"
			+ " fringilla vel, aliquet nec, vulputate eget, arcu";
	
	public final static String COMMENT_TWO = " Donec vitae sapien ut libero venenatis faucibus."
			+ "Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt.";
	
	public final static String COMMENT_NEW = "Curabitur at lacus ac velit ornare lobortis."
			+ " Curabitur a felis in nunc fringilla tristique amet orci eget eros.";
	
	public final static long REPLY_ONE_ID = 1L;
	public final static long REPLY_TWO_ID = 2L;
	public final static long REPLY_NEW_ID = 3L;
	public final static String REPLY_ONE = "Donec sodales sagittis magna. Sed consequat,"
			+ " leo eget bibendum sodales, augue velit cursus nunc";
	
	public final static String REPLY_TWO = " Donec vitae sapien ut libero venenatis faucibus."
			+ "Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt.";
	
	public final static String REPLY_NEW = "Pellentesque habitant morbi tristique senectus"
			+ " et netus et malesuada fames ac turpis egestas. Nullam quis ante.";
	
	public final static Language LANGUAGE = Language.EN;
	public final static String SITE_NAME = "Web Blog App";
	public final static String SITE_ADDRESS = "https://blogapp.com";
	public final static String SITE_EMAIL = "master@blogapp.com";
	public final static String SITE_META_DESC = "Web blog with cool stuffs";
	public final static String SITE_META_KEY = "Tecnology, Computer";
	public final static String SMTP_SERVER = "smtp.blogapp.com";
	public final static String SMTP_PORT = "25";
	public final static boolean SMTP_USE_TLS = true;
	public final static String SMTP_USER = "blogapp";
	public final static String SMTP_PASSWORD = "secret";
	public final static String SMTP_PASSWORD_UPDATE = "#secret#";
	
	public final static long MEDIA_ONE_ID = 1L;
	public final static long MEDIA_TWO_ID = 2L;
	public final static long MEDIA_NEW_ID = 3L;
	public final static long MEDIA_OUT_ID = 4L;
	
	public final static String MEDIA_ONE_NAME = "bestblogs.zip";
	public final static String MEDIA_TWO_NAME = "wallpaper.png";
	public final static String MEDIA_NEW_NAME = "bookslist.zip";
	
	public final static String MEDIA_ONE_DESC = "List of best blogs in 2012";
	public final static String MEDIA_TWO_DESC = "Amazing nature wallpaper";
	public final static String MEDIA_NEW_DESC = "List of books to read";
	
	public final static String MEDIA_ONE_URN = "/files/2012/9/bestblogs.zip";
	public final static String MEDIA_TWO_URN = "/images/2012/9/wallpaper.png";
	public final static String MEDIA_NEW_URN = "/files/2012/9/bookslist.zip";
	
	public final static String MEDIA_ONE_SURN = "/" + MEDIA_ONE_ID + "-" + MEDIA_ONE_NAME;
	public final static String MEDIA_TWO_SURN = "/" + MEDIA_TWO_ID + "-" + MEDIA_TWO_NAME;
	
	public final static byte[] MEDIA_ONE_BIN = new byte[] {56, 88, 98, 38, 12, 98, 74, 61};
	public final static byte[] MEDIA_TWO_BIN = new byte[] {46, 68, 28, 18, 82, 58, 44, 79};
	
	public final static Type MEDIA_ONE_TYPE = Type.ZIP;
	public final static Type MEDIA_TWO_TYPE = Type.IMG;
	
	public final static String CONTENT_TYPE_ONE = "application/zip";
	public final static String CONTENT_TYPE_TWO = "image/png";
	
	public final static long TOTAL_POSTS = 10;
	public final static long PAGE_TWO = 2;
	public final static long PAGE_NG = 4;
	public final static long BEGIN_ZERO = 0;
	public final static long BEGIN_FOUR = 4;
	public final static long BEGIN_NG = 12;
	public final static int MAX_PAGES = 4;
	
	public final static String USER_UNKNOWN = "unknown";
	public final static String PASS_WRONG = "passwrong";
	
	public final static String TEST_CONTEXT = "http://localhost:8080/jsfblog";
	public final static String INVALID_URI = "/index.xhtml";
	public final static String UNAVAILABLE_URI = "/category2.jsf";
	public final static String ERROR_404 = "Error 404";
	public final static String ERROR_403 = "Error 403";
	
	public final static String REGISTER_URI = "/register.jsf";
	public final static String PG_TITLE = "Page title";
	public final static String REGISTER_PG_TITLE = "User Register";
	public final static String LOGIN_PG_TITLE = "Enter - Login";
	public final static String MSG_USER_NA = "Username not available";
	
	public final static String CATEGORY_URI = "/admin/category.jsf";
	public final static String CATEGORY_PG_TITLE = "Administration | Category";
	
	public final static String LOGIN_URI = "/login.jsf";
	public final static String USER_HOME_URI = "/home/home.jsf";
	public final static String INDEX_URI = "/index.jsf";
	public final static String USER_HOME_PG_TITLE = "User | Home";

	public final static String USERS_URI = "/admin/users.jsf";
	public final static String USERS_PG_TITLE = "Administration | Users";
	
	public final static String SETTING_URI = "/admin/setting.jsf";
	public final static String SETTING_PG_TITLE = "Administration | Setting";
	public final static String SETTING_PG_TITLE_PT = "Administração | Configuração";
	public final static String ADMIN_HOME_PG_TITLE = "Administration | Home";

	public final static String PROFILE_URI = "/home/profile.jsf";
	public final static String PROFILE_PG_TITLE = "Profile";
	
	public final static String POST_URI = "/home/post.jsf";
	public final static String POST_PG_TITLE = "User | Publish";
	
	public final static String POSTS_URI = "/home/posts.jsf";
	public final static String POSTS_PG_TITLE = "User | Publications";
	
	public final static String INDEX_PG_TITLE = SITE_NAME + " | Publications";
	public final static String VIEW_TITLE = "One morning, when Gregor Samsa";
	public final static String VIEW_META_DESC = "One morning, when Gregor Samsa woke";
	public final static String VIEW_META_KEY = "Kafka";
	public final static String PG_META_DESC = "Meta Description";
	public final static String PG_META_KEY = "Meta Keyword";
}

