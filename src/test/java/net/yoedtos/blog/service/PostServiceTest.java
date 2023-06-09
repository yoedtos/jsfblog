package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.CATEGORY_ID;
import static net.yoedtos.blog.util.TestConstants.CONTENT;
import static net.yoedtos.blog.util.TestConstants.CONTENT_UPDATE;
import static net.yoedtos.blog.util.TestConstants.INTRO;
import static net.yoedtos.blog.util.TestConstants.INTRO_UPDATE;
import static net.yoedtos.blog.util.TestConstants.META_DESC;
import static net.yoedtos.blog.util.TestConstants.META_KEY;
import static net.yoedtos.blog.util.TestConstants.POST_ID;
import static net.yoedtos.blog.util.TestConstants.TITLE;
import static net.yoedtos.blog.util.TestConstants.TITLE_UPDATE;
import static net.yoedtos.blog.util.TestConstants.USERNAME;
import static net.yoedtos.blog.util.TestUtil.createCategory;
import static net.yoedtos.blog.util.TestUtil.createPostDTO;
import static net.yoedtos.blog.util.TestUtil.createPostOne;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.model.dto.PostDTO;
import net.yoedtos.blog.model.entity.Category;
import net.yoedtos.blog.model.entity.Post;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.dao.CategoryDao;
import net.yoedtos.blog.repository.dao.PostDao;
import net.yoedtos.blog.repository.dao.UserDao;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

	@Mock
	private CategoryDao categoryDao;

	@Mock
	private UserDao userDao;
	
	@Mock
	private PostDao postDaoMock;

	@InjectMocks
	private PostService postService;

	@Captor
	private ArgumentCaptor<?> captor;

	private Date today;
	private PostDTO postDTO;
	private Post postOne;
	private Category category;
	private User userOne;

	@Before
	public void init() {
		today = new Date();

		postDTO = createPostDTO(today);
		category = createCategory();
		userOne = createUserOne(today);
		postOne = createPostOne(today, userOne, category);
	}

	@Test
	public void whenCreateShouldCreateOnce() throws ServiceException, DaoException {
		when(categoryDao.findById(CATEGORY_ID)).thenReturn(category);
		when(userDao.findByUsername(postDTO.getAuthor())).thenReturn(userOne);
		
		postService.create(postDTO);
		verify(postDaoMock).persist((Post) captor.capture());
		Post post = (Post) captor.getValue();

		assertThat(post.getId(), is(POST_ID));
		assertThat(post.getCreatedAt(), is(today));
		assertThat(post.getTitle(), is(TITLE));
		assertThat(post.getAuthor(), is(userOne));
		assertThat(post.getCategory(), is(category));
		assertThat(post.getIntro(), is(INTRO));
		assertThat(post.getContent(), is(CONTENT));
		assertThat(post.getMetaDesc(), is(META_DESC));
		assertThat(post.getMetaKey(), is(META_KEY));
		
		verify(categoryDao, times(1)).findById(CATEGORY_ID);
		verify(userDao, times(1)).findByUsername(USERNAME);
		verify(postDaoMock, times(1)).persist(post);
	}

	@Test
	public void whenRemoveShouldRemoveOnce() throws DaoException, ServiceException {
		postService.remove(POST_ID);
		verify(postDaoMock).remove((Long) captor.capture());
		assertEquals(POST_ID, ((Long) captor.getValue()).intValue());
		verify(postDaoMock, times(1)).remove(POST_ID);
	}

	@Test(expected = ServiceException.class)
	public void whenCreateShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(postDaoMock).persist(postOne);
		postService.create(postDTO);
		verify(postDaoMock, times(1)).persist(postOne);
	}

	@Test(expected = ServiceException.class)
	public void whenRemoveShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(postDaoMock).remove(POST_ID);
		postService.remove(POST_ID);
		verify(postDaoMock, times(1)).remove(POST_ID);
	}

	@Test
	public void whenGetShouldReturnPostDTO() throws DaoException, ServiceException {
		when(postDaoMock.findById(POST_ID)).thenReturn(postOne);
		PostDTO postDTO = postService.get(POST_ID);
		
		assertThat(postDTO.getId(), is(postOne.getId()));
		assertThat(postDTO.getCreatedAt(), is(postOne.getCreatedAt()));
		assertThat(postDTO.getTitle(), is(postOne.getTitle()));
		assertThat(postDTO.getAuthor(), is(postOne.getAuthor().getUsername()));
		assertThat(postDTO.getCategoryId(), is(postOne.getCategory().getId()));
		assertThat(postDTO.getIntro(), is(postOne.getIntro()));
		assertThat(postDTO.getContent(), is(postOne.getContent()));
		assertThat(postDTO.getMetaDesc(), is(postOne.getMetaDesc()));
		assertThat(postDTO.getMetaKey(), is(postOne.getMetaKey()));
		
		verify(postDaoMock, times(1)).findById(POST_ID);
	}

	@Test
	public void whenUpdateShouldReturnPostUpdated() throws DaoException, ServiceException {
		Post postUpdate = new Post.Builder()
				.id(POST_ID)
				.createdAt(today)
				.title(TITLE_UPDATE)
				.author(userOne)
				.category(category)
				.intro(INTRO_UPDATE)
				.content(CONTENT_UPDATE)
				.metaDesc(META_DESC)
				.metaKey(META_KEY)
				.build();
		
		postDTO = new PostDTO.Builder(postDTO.getAuthor())
				.postId(postDTO.getId()).createdAt(today)
				.title(TITLE_UPDATE)
				.categoryId(postDTO.getCategoryId())
				.intro(INTRO_UPDATE)
				.content(CONTENT_UPDATE)
				.metaDesc(META_DESC)
				.metaKey(META_KEY)
				.build();

		when(postDaoMock.findById(postDTO.getId())).thenReturn(postOne);
		when(postDaoMock.merge(postOne)).thenReturn(postUpdate);

		PostDTO dbPostDTO = postService.update(postDTO);

		assertThat(dbPostDTO.getId(), is(POST_ID));
		assertThat(dbPostDTO.getCreatedAt(), is(today));
		assertThat(dbPostDTO.getTitle(), is(TITLE_UPDATE));
		assertThat(dbPostDTO.getAuthor(), is(USERNAME));
		assertThat(dbPostDTO.getCategoryId(), is(CATEGORY_ID));
		assertThat(dbPostDTO.getIntro(), is(INTRO_UPDATE));
		assertThat(dbPostDTO.getContent(), is(CONTENT_UPDATE));
		assertThat(dbPostDTO.getMetaDesc(), is(META_DESC));
		assertThat(dbPostDTO.getMetaKey(), is(META_KEY));

		verify(postDaoMock, times(1)).findById(postDTO.getId());
		verify(postDaoMock, times(1)).merge(postOne);
	}

	@Test(expected = ServiceException.class)
	public void whenGetAllShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(postDaoMock).findAll();
		postService.getAll();
		verify(postDaoMock, times(1)).findAll();
	}

	@Test
	public void whenGetAllShouldReturnTwoPosts() throws ServiceException, DaoException {
		Post postTwo = new Post.Builder()
				.id(2L)
				.createdAt(today)
				.title(TITLE_UPDATE)
				.author(userOne)
				.category(category)
				.intro(INTRO_UPDATE)
				.content(CONTENT_UPDATE)
				.metaDesc(META_DESC)
				.metaKey(META_KEY)
				.build();

		List<Post> posts = Arrays.asList(postOne, postTwo);
		when(postDaoMock.findAll()).thenReturn(posts);
		List<PostDTO> postDTOs = postService.getAll();
		assertEquals(2, postDTOs.size());

		int index = 0;
		for (PostDTO postDTO : postDTOs) {
			assertThat(postDTO.getId(), is(posts.get(index).getId()));
			assertThat(postDTO.getCreatedAt(), is(posts.get(index).getCreatedAt()));
			assertThat(postDTO.getTitle(), is(posts.get(index).getTitle()));
			assertThat(postDTO.getAuthor(), is(posts.get(index).getAuthor().getUsername()));
			assertThat(postDTO.getCategoryId(), is(posts.get(index).getCategory().getId()));
			assertThat(postDTO.getIntro(), is(posts.get(index).getIntro()));
			assertThat(postDTO.getContent(), is(posts.get(index).getContent()));
			assertThat(postDTO.getMetaDesc(), is(posts.get(index).getMetaDesc()));
			assertThat(postDTO.getMetaKey(), is(posts.get(index).getMetaKey()));
			index++;
		}
	}
}
