package net.yoedtos.blog.util;

import static net.yoedtos.blog.util.TestConstants.AUTHOR_ONE;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_ONE;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.COMMENT_ONE;
import static net.yoedtos.blog.util.TestConstants.COMMENT_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.CONTENT_ONE;
import static net.yoedtos.blog.util.TestConstants.CONTENT_TWO;
import static net.yoedtos.blog.util.TestConstants.CONTENT_TYPE_ONE;
import static net.yoedtos.blog.util.TestConstants.EMAIL_ONE;
import static net.yoedtos.blog.util.TestConstants.EMAIL_TWO;
import static net.yoedtos.blog.util.TestConstants.FULLNAME_ONE;
import static net.yoedtos.blog.util.TestConstants.FULLNAME_TWO;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.INTRO_ONE;
import static net.yoedtos.blog.util.TestConstants.INTRO_TWO;
import static net.yoedtos.blog.util.TestConstants.LANGUAGE;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_DESC;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_NAME;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_TYPE;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_URN;
import static net.yoedtos.blog.util.TestConstants.META_DESC;
import static net.yoedtos.blog.util.TestConstants.META_KEY;
import static net.yoedtos.blog.util.TestConstants.PASSWORD;
import static net.yoedtos.blog.util.TestConstants.POST_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.POST_TWO_ID;
import static net.yoedtos.blog.util.TestConstants.REPLY_ONE;
import static net.yoedtos.blog.util.TestConstants.REPLY_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.SITE_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.SITE_EMAIL;
import static net.yoedtos.blog.util.TestConstants.SITE_META_DESC;
import static net.yoedtos.blog.util.TestConstants.SITE_META_KEY;
import static net.yoedtos.blog.util.TestConstants.SITE_NAME;
import static net.yoedtos.blog.util.TestConstants.SMTP_PASSWORD;
import static net.yoedtos.blog.util.TestConstants.SMTP_PORT;
import static net.yoedtos.blog.util.TestConstants.SMTP_SERVER;
import static net.yoedtos.blog.util.TestConstants.SMTP_USER;
import static net.yoedtos.blog.util.TestConstants.SMTP_USE_TLS;
import static net.yoedtos.blog.util.TestConstants.TITLE_ONE;
import static net.yoedtos.blog.util.TestConstants.TITLE_TWO;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestConstants.USERNAME_TWO;
import static net.yoedtos.blog.util.TestConstants.USER_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.USER_TWO_ID;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.time.DateUtils;

import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.dto.CommentDTO;
import net.yoedtos.blog.model.dto.MediaDTO;
import net.yoedtos.blog.model.dto.MessageDTO;
import net.yoedtos.blog.model.dto.PostDTO;
import net.yoedtos.blog.model.dto.ReplyDTO;
import net.yoedtos.blog.model.dto.SettingDTO;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.model.entity.Category;
import net.yoedtos.blog.model.entity.Comment;
import net.yoedtos.blog.model.entity.Media;
import net.yoedtos.blog.model.entity.Message;
import net.yoedtos.blog.model.entity.Post;
import net.yoedtos.blog.model.entity.Reply;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.fs.PropsKey;
import net.yoedtos.blog.search.Found;
import net.yoedtos.blog.view.i18n.Language;

public class TestUtil {

	private TestUtil() {}
	
	public static UserDTO createUserDTO(Date createAt) {
		return new UserDTO.Builder(USERNAME_ONE)
				.fullname(FULLNAME_ONE)
				.email(EMAIL_ONE)
				.password(PASSWORD)
				.hostAddress(HOST_ADDRESS)
				.role(Role.MEMBER)
				.active(true)
				.createdAt(createAt)
				.build();
	}
	
	public static User createUserOne(Date createAt) {
		 return new User.Builder(USERNAME_ONE)
				.userId(USER_ONE_ID)
				.fullname(FULLNAME_ONE)
				.email(EMAIL_ONE)
				.hostAddress(HOST_ADDRESS)
				.role(Role.MEMBER)
				.active(true)
				.createAt(createAt)
				.build();
	}
	
	public static User createUserTwo(Date createAt) {
		return new User.Builder(USERNAME_TWO)
			.userId(USER_TWO_ID)
			.fullname(FULLNAME_TWO)
			.email(EMAIL_TWO)
			.hostAddress(HOST_ADDRESS)
			.role(Role.MEMBER)
			.active(true)
			.createAt(createAt)
			.build();
	}
	
	public static PostDTO createPostDTO(Date createAt) {
		return new PostDTO.Builder(USERNAME_ONE)
				.postId(POST_ONE_ID)
				.createdAt(createAt)
				.title(TITLE_ONE)
				.categoryId(CATEGORY_ONE_ID)
				.intro(INTRO_ONE)
				.content(CONTENT_ONE)
				.metaDesc(META_DESC)
				.metaKey(META_KEY)
				.build();
	}
	
	public static Category createCategory() {
		Category category = new Category();
		category.setId(CATEGORY_ONE_ID);
		category.setValue(CATEGORY_ONE);
		return category;
	}
	
	public static Post createPostOne(Date createtAt, User userOne, Category category) {
		return new Post.Builder()
				.id(POST_ONE_ID)
				.createdAt(createtAt)
				.title(TITLE_ONE)
				.author(userOne)
				.category(category)
				.intro(INTRO_ONE)
				.content(CONTENT_ONE)
				.metaDesc(META_DESC)
				.metaKey(META_KEY)
				.build();
	}
	
	public static Post createPostTwo(Date createtAt, User userTwo, Category category) {
		return new Post.Builder()
				.id(POST_TWO_ID)
				.createdAt(createtAt)
				.title(TITLE_TWO)
				.author(userTwo)
				.category(category)
				.intro(INTRO_TWO)
				.content(CONTENT_TWO)
				.metaDesc(META_DESC)
				.metaKey(META_KEY)
				.build();
	}
	
	public static CommentDTO createCommentDTO(Date createAt) {
		return new CommentDTO.Builder(AUTHOR_ONE)
				.commentId(COMMENT_ONE_ID)
				.createAt(createAt)
				.content(COMMENT_ONE)
				.email(EMAIL_ONE)
				.hostAddress(HOST_ADDRESS)
				.postId(POST_ONE_ID)
				.build();
	}
	
	public static Comment createCommentOne(Date createAt, Post post) {
		return new Comment.Builder(AUTHOR_ONE)
				.id(COMMENT_ONE_ID)
				.createAt(createAt)
				.content(COMMENT_ONE)
				.email(EMAIL_ONE)
				.hostAddress(HOST_ADDRESS)
				.post(post)
				.build();
	}
	
	public static ReplyDTO createReplyDTO(Date createAt) {
		return new ReplyDTO.Builder(USERNAME_ONE)
				.replyId(REPLY_ONE_ID)
				.commentId(COMMENT_ONE_ID)
				.createAt(createAt)
				.content(REPLY_ONE)
				.hostAddress(HOST_ADDRESS)
				.build();
	}
	
	public static Reply createReplyOne(Date createAt, Comment comment, User author) {
		return new Reply.Builder()
				.id(REPLY_ONE_ID)
				.createAt(createAt)
				.content(REPLY_ONE)
				.hostAddress(HOST_ADDRESS)
				.author(author)
				.comment(comment)
				.build();
	}
	
	public static Date createDate(String dateStr) {
		Date date;
		try {
			date = DateUtils.parseDate(dateStr, new String[]{"yyyy-MM-dd HH:mm:ss.SSS"});
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
		return date;
	}
	
	public static long changeToSeconds(Date date) {
		return date.getTime()/1000;
	}
	
	public static SettingDTO createSettingDTO() {
		return new SettingDTO.Builder()
				.language(LANGUAGE)
				.siteName(SITE_NAME)
				.siteAddress(SITE_ADDRESS)
				.siteEmail(SITE_EMAIL)
				.siteMetaDesc(SITE_META_DESC)
				.siteMetaKey(SITE_META_KEY)
				.smtpServer(SMTP_SERVER)
				.smtpPort(SMTP_PORT)
				.useTLS(SMTP_USE_TLS)
				.smtpUser(SMTP_USER)
				.smtpPassword(SMTP_PASSWORD)
				.build();
	}
	
	public static Properties createSettings() {
		Properties settings = new Properties();
		settings.setProperty(PropsKey.LANGUAGE, Language.EN.name());
		settings.setProperty(PropsKey.SITE_NAME, SITE_NAME);
		settings.setProperty(PropsKey.SITE_ADDRESS, SITE_ADDRESS);
		settings.setProperty(PropsKey.SITE_EMAIL, SITE_EMAIL);
		settings.setProperty(PropsKey.SITE_META_DESC, SITE_META_DESC);
		settings.setProperty(PropsKey.SITE_META_KEY, SITE_META_KEY);
		settings.setProperty(PropsKey.SMTP_SERVER, SMTP_SERVER);
		settings.setProperty(PropsKey.SMTP_PORT, SMTP_PORT);
		settings.setProperty(PropsKey.SMTP_USE_TLS, Boolean.toString(SMTP_USE_TLS));
		settings.setProperty(PropsKey.SMTP_USER, SMTP_USER);
		settings.setProperty(PropsKey.SMTP_PASSWORD, SMTP_PASSWORD);
		
		return settings;
	}
	
	public static MediaDTO createMediaDTO(Date dateOne) {
		return new MediaDTO.Builder()
				.createAt(dateOne)
				.owner(USERNAME_ONE)
				.name(MEDIA_ONE_NAME)
				.description(MEDIA_ONE_DESC)
				.contentType(CONTENT_TYPE_ONE)
				.type(MEDIA_ONE_TYPE)
				.build();
	}
	
	public static Media createMediaOne(Date dateOne) {
		return new Media.Builder()
				.id(MEDIA_ONE_ID)
				.createAt(dateOne)
				.owner(createUserOne(dateOne))
				.name(MEDIA_ONE_NAME)
				.description(MEDIA_ONE_DESC)
				.contentType(CONTENT_TYPE_ONE)
				.urn(MEDIA_ONE_URN)
				.build();
	}
	
	public static Media createMedia(Date dateOne) {
		return new Media.Builder()
				.createAt(dateOne)
				.owner(createUserOne(dateOne))
				.name(MEDIA_ONE_NAME)
				.description(MEDIA_ONE_DESC)
				.contentType(CONTENT_TYPE_ONE)
				.urn(MEDIA_ONE_URN)
				.build();
	}
	
	public static String createUrnOne(String mediaName) {
		Calendar today = Calendar.getInstance();
		return "/" + Constants.FILES + "/" 
				+ today.get(Calendar.YEAR) 
				+ "/" + (today.get(Calendar.MONTH) + 1) 
				+ "/" + mediaName;
	}
	
	public static String loadMediaFile(String filename) {
		ClassLoader classloader = TestUtil.class.getClassLoader();
		File resource = new File(classloader.getResource(filename).getFile());
		return resource.getAbsolutePath();
	}
	
	public static Found createFoundOne() {
		return new Found(POST_ONE_ID, TITLE_ONE, META_DESC);
	}
	
	public static Found createFoundTwo() {
		return new Found(POST_TWO_ID, TITLE_TWO, META_DESC);
	}
	
	public static MessageDTO createMessageDTO() {
		return new MessageDTO.Builder()
				.senderName(FULLNAME_ONE)
				.senderEmail(EMAIL_ONE)
				.subject(TITLE_ONE)
				.content(CONTENT_ONE)
				.hostAddress(HOST_ADDRESS)
				.build();
	}
	
	public static Message createMessageOne(Date createAt, Long id) {
		return new Message.Builder()
				.id(id)
				.createAt(createAt)
				.senderName(FULLNAME_ONE)
				.senderEmail(EMAIL_ONE)
				.subject(TITLE_ONE)
				.content(CONTENT_ONE)
				.hostAddress(HOST_ADDRESS)
				.build();
	}
	
	public static Message createMessageTwo(Date createAt, Long id) {
		return new Message.Builder()
				.id(id)
				.createAt(createAt)
				.senderName(FULLNAME_TWO)
				.senderEmail(EMAIL_TWO)
				.subject(TITLE_TWO)
				.content(CONTENT_TWO)
				.hostAddress(HOST_ADDRESS)
				.build();
	}
}