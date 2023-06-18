package net.yoedtos.blog.util;

import static net.yoedtos.blog.util.TestConstants.AUTHOR_ONE;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_ONE;
import static net.yoedtos.blog.util.TestConstants.COMMENT_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.COMMENT_ONE;
import static net.yoedtos.blog.util.TestConstants.CONTENT_ONE;
import static net.yoedtos.blog.util.TestConstants.EMAIL_ONE;
import static net.yoedtos.blog.util.TestConstants.FULLNAME_ONE;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.INTRO_ONE;
import static net.yoedtos.blog.util.TestConstants.META_DESC;
import static net.yoedtos.blog.util.TestConstants.META_KEY;
import static net.yoedtos.blog.util.TestConstants.PASSWORD;
import static net.yoedtos.blog.util.TestConstants.POST_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.REPLY_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.REPLY_ONE;
import static net.yoedtos.blog.util.TestConstants.TITLE_ONE;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestConstants.USER_ONE_ID;

import java.util.Date;

import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.dto.CommentDTO;
import net.yoedtos.blog.model.dto.PostDTO;
import net.yoedtos.blog.model.dto.ReplyDTO;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.model.entity.Category;
import net.yoedtos.blog.model.entity.Comment;
import net.yoedtos.blog.model.entity.Post;
import net.yoedtos.blog.model.entity.Reply;
import net.yoedtos.blog.model.entity.User;

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
}
