package net.yoedtos.blog.util;

import static net.yoedtos.blog.util.TestConstants.AUTHOR;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_ID;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_VALUE;
import static net.yoedtos.blog.util.TestConstants.COMMENT_ID;
import static net.yoedtos.blog.util.TestConstants.COMMENT_VALUE;
import static net.yoedtos.blog.util.TestConstants.CONTENT;
import static net.yoedtos.blog.util.TestConstants.EMAIL;
import static net.yoedtos.blog.util.TestConstants.FULLNAME;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.INTRO;
import static net.yoedtos.blog.util.TestConstants.META_DESC;
import static net.yoedtos.blog.util.TestConstants.META_KEY;
import static net.yoedtos.blog.util.TestConstants.PASSWORD;
import static net.yoedtos.blog.util.TestConstants.POST_ID;
import static net.yoedtos.blog.util.TestConstants.REPLY_ID;
import static net.yoedtos.blog.util.TestConstants.REPLY_VALUE;
import static net.yoedtos.blog.util.TestConstants.TITLE;
import static net.yoedtos.blog.util.TestConstants.USERNAME;
import static net.yoedtos.blog.util.TestConstants.USER_ID;

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
		return new UserDTO.Builder(USERNAME)
				.fullname(FULLNAME)
				.email(EMAIL)
				.password(PASSWORD)
				.hostAddress(HOST_ADDRESS)
				.role(Role.MEMBER)
				.active(true)
				.createdAt(createAt)
				.build();
	}
	
	public static User createUserOne(Date createAt) {
		 return new User.Builder(USERNAME)
				.userId(USER_ID)
				.fullname(FULLNAME)
				.email(EMAIL)
				.hostAddress(HOST_ADDRESS)
				.role(Role.MEMBER)
				.active(true)
				.createAt(createAt)
				.build();
	}
	
	public static PostDTO createPostDTO(Date createAt) {
		return new PostDTO.Builder(USERNAME)
				.postId(POST_ID)
				.createdAt(createAt)
				.title(TITLE)
				.categoryId(CATEGORY_ID)
				.intro(INTRO)
				.content(CONTENT)
				.metaDesc(META_DESC)
				.metaKey(META_KEY)
				.build();
	}
	
	public static Category createCategory() {
		Category category = new Category();
		category.setId(CATEGORY_ID);
		category.setValue(CATEGORY_VALUE);
		return category;
	}
	
	public static Post createPostOne(Date createtAt, User userOne, Category category) {
		return new Post.Builder()
				.id(POST_ID)
				.createdAt(createtAt)
				.title(TITLE)
				.author(userOne)
				.category(category)
				.intro(INTRO)
				.content(CONTENT)
				.metaDesc(META_DESC)
				.metaKey(META_KEY)
				.build();
	}
	
	public static CommentDTO createCommentDTO(Date createAt) {
		return new CommentDTO.Builder(AUTHOR)
				.commentId(COMMENT_ID)
				.createAt(createAt)
				.content(COMMENT_VALUE)
				.email(EMAIL)
				.hostAddress(HOST_ADDRESS)
				.postId(POST_ID)
				.build();
	}
	
	public static Comment createCommentOne(Date createAt, Post post) {
		return new Comment.Builder(AUTHOR)
				.id(COMMENT_ID)
				.createAt(createAt)
				.content(COMMENT_VALUE)
				.email(EMAIL)
				.hostAddress(HOST_ADDRESS)
				.post(post)
				.build();
	}
	
	public static ReplyDTO createReplyDTO(Date createAt) {
		return new ReplyDTO.Builder(USERNAME)
				.replyId(REPLY_ID)
				.commentId(COMMENT_ID)
				.createAt(createAt)
				.content(REPLY_VALUE)
				.hostAddress(HOST_ADDRESS)
				.build();
	}
	
	public static Reply createReplyOne(Date createAt, Comment comment, User author) {
		return new Reply.Builder()
				.id(REPLY_ID)
				.createAt(createAt)
				.content(REPLY_VALUE)
				.hostAddress(HOST_ADDRESS)
				.author(author)
				.comment(comment)
				.build();
	}
}
