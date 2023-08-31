package net.yoedtos.blog.util;

public class Constants {
	private Constants() {}
	
	public static final String PASS_MASK = "*******";
	public static final String PERSIST_UNIT = "blogdb";
	public static final String PROPERTY_FILE_HEADER = "jsfblog settings";
	public static final String IMAGES = "images";
	public static final String VIDEOS = "videos";
	public static final String DOCS = "docs";
	public static final String FILES = "files";
	public static final String DATA_PATH = System.getProperty("user.home") + "/data/jsfblog";
	public static final String MEDIA_PATH = DATA_PATH + "/media";
	public static final String SLASH = "/";
	public static final int MAX_PAGE_SIZE = 4;
	public static final String USER_INACTIVE = "Error: user inactive";
	public static final String USER_PASS_NG = "Error: user password";
	public final static String ADMIN_USER = "admin-user";
	public final static String ADMIN_USER_PASS = "abcde";
	public final static String SCRIPT_ADMIN = "INSERT IGNORE INTO user(user_id, username, password, role, active) "
			+ "VALUES ('0', 'admin-user', 'dMI3KYBrFlUr48MfjwowhC0yMqWoqpR97bYWs7CZd9B0bG0hxiSsBv92xo5SGUpB', 'ADMINISTRATOR', true);";
	public final static String DEMO_DATA = DATA_PATH + "/demo-data.sql";
	public static final String APP_PROPS = DATA_PATH + "/configuration.properties";
	public static final String PROPERTY_FILE_NAME = DATA_PATH + "/jsfblog.properties";
	public static final String KEY_SEARCH_INDEX = "hibernate.search.default.indexBase";
}
