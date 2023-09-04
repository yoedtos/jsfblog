package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.CATEGORY_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.CONTENT_ONE;
import static net.yoedtos.blog.util.TestConstants.CONTENT_TWO;
import static net.yoedtos.blog.util.TestConstants.CONTENT_UPDATE;
import static net.yoedtos.blog.util.TestConstants.INTRO_ONE;
import static net.yoedtos.blog.util.TestConstants.INTRO_TWO;
import static net.yoedtos.blog.util.TestConstants.INTRO_UPDATE;
import static net.yoedtos.blog.util.TestConstants.META_DESC;
import static net.yoedtos.blog.util.TestConstants.META_KEY;
import static net.yoedtos.blog.util.TestConstants.POST_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.TITLE_ONE;
import static net.yoedtos.blog.util.TestConstants.TITLE_TWO;
import static net.yoedtos.blog.util.TestConstants.TITLE_UPDATE;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestUtil.changeToSeconds;
import static net.yoedtos.blog.util.TestUtil.createCategory;
import static net.yoedtos.blog.util.TestUtil.createPostDTO;
import static net.yoedtos.blog.util.TestUtil.createPostOne;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static org.hamcrest.CoreMatchers.equalTo;
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

		postDTO = createPostDTO(null);
		category = createCategory();
		userOne = createUserOne(today);
		postOne = createPostOne(today, userOne, category);
	}

	@Test
	public void whenCreateShouldCreateOnce() throws ServiceException, DaoException {
		when(categoryDao.findById(CATEGORY_ONE_ID)).thenReturn(category);
		when(userDao.findByUsername(postDTO.getAuthor())).thenReturn(userOne);
		
		postService.create(postDTO);
		verify(postDaoMock).persist((Post) captor.capture());
		Post post = (Post) captor.getValue();

		assertThat(post.getId(), equalTo(POST_ONE_ID));
		assertThat(changeToSeconds(post.getCreatedAt()), equalTo(changeToSeconds(today)));
		assertThat(post.getTitle(), equalTo(TITLE_ONE));
		assertThat(post.getAuthor(), equalTo(userOne));
		assertThat(post.getCategory(), equalTo(category));
		assertThat(post.getIntro(), equalTo(INTRO_ONE));
		assertThat(post.getContent(), equalTo(CONTENT_ONE));
		assertThat(post.getMetaDesc(), equalTo(META_DESC));
		assertThat(post.getMetaKey(), equalTo(META_KEY));
		
		verify(categoryDao, times(1)).findById(CATEGORY_ONE_ID);
		verify(userDao, times(1)).findByUsername(USERNAME_ONE);
		verify(postDaoMock, times(1)).persist(post);
	}

	@Test
	public void whenRemoveShouldRemoveOnce() throws DaoException, ServiceException {
		postService.remove(POST_ONE_ID);
		verify(postDaoMock).remove((Long) captor.capture());
		assertEquals(POST_ONE_ID, ((Long) captor.getValue()).intValue());
		verify(postDaoMock, times(1)).remove(POST_ONE_ID);
	}

	@Test(expected = ServiceException.class)
	public void whenCreateShouldThrowsException() throws DaoException, ServiceException {
		when(categoryDao.findById(CATEGORY_ONE_ID)).thenReturn(category);
		when(userDao.findByUsername(postDTO.getAuthor())).thenReturn(userOne);
		
		doThrow(new DaoException()).when(postDaoMock).persist(postOne);
		postService.create(postDTO);
		verify(postDaoMock, times(1)).persist(postOne);
	}

	@Test(expected = ServiceException.class)
	public void whenRemoveShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(postDaoMock).remove(POST_ONE_ID);
		postService.remove(POST_ONE_ID);
		verify(postDaoMock, times(1)).remove(POST_ONE_ID);
	}

	@Test
	public void whenGetShouldReturnPostDTO() throws DaoException, ServiceException {
		when(postDaoMock.findById(POST_ONE_ID)).thenReturn(postOne);
		PostDTO postDTO = postService.get(POST_ONE_ID);
		
		assertThat(postDTO.getId(), equalTo(postOne.getId()));
		assertThat(postDTO.getCreatedAt(), equalTo(postOne.getCreatedAt()));
		assertThat(postDTO.getTitle(), equalTo(postOne.getTitle()));
		assertThat(postDTO.getAuthor(), equalTo(postOne.getAuthor().getUsername()));
		assertThat(postDTO.getCategoryId(), equalTo(postOne.getCategory().getId()));
		assertThat(postDTO.getIntro(), equalTo(postOne.getIntro()));
		assertThat(postDTO.getContent(), equalTo(postOne.getContent()));
		assertThat(postDTO.getMetaDesc(), equalTo(postOne.getMetaDesc()));
		assertThat(postDTO.getMetaKey(), equalTo(postOne.getMetaKey()));
		
		verify(postDaoMock, times(1)).findById(POST_ONE_ID);
	}

	@Test
	public void whenUpdateShouldReturnPostUpdated() throws DaoException, ServiceException {
		Post postUpdate = new Post.Builder()
				.id(POST_ONE_ID)
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
				.postId(postDTO.getId())
				.createdAt(today)
				.title(TITLE_UPDATE)
				.categoryId(postDTO.getCategoryId())
				.intro(INTRO_UPDATE)
				.content(CONTENT_UPDATE)
				.metaDesc(META_DESC)
				.metaKey(META_KEY)
				.build();

		when(postDaoMock.findById(postDTO.getId())).thenReturn(postOne);
		when(postDaoMock.merge(postUpdate)).thenReturn(postUpdate);

		PostDTO dbPostDTO = postService.update(postDTO);

		assertThat(dbPostDTO.getId(), equalTo(POST_ONE_ID));
		assertThat(dbPostDTO.getCreatedAt(), equalTo(today));
		assertThat(dbPostDTO.getTitle(), equalTo(TITLE_UPDATE));
		assertThat(dbPostDTO.getAuthor(), equalTo(USERNAME_ONE));
		assertThat(dbPostDTO.getCategoryId(), equalTo(CATEGORY_ONE_ID));
		assertThat(dbPostDTO.getIntro(), equalTo(INTRO_UPDATE));
		assertThat(dbPostDTO.getContent(), equalTo(CONTENT_UPDATE));
		assertThat(dbPostDTO.getMetaDesc(), equalTo(META_DESC));
		assertThat(dbPostDTO.getMetaKey(), equalTo(META_KEY));

		verify(postDaoMock, times(1)).findById(postDTO.getId());
		verify(postDaoMock, times(1)).merge(postUpdate);
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
				.title(TITLE_TWO)
				.author(userOne)
				.category(category)
				.intro(INTRO_TWO)
				.content(CONTENT_TWO)
				.metaDesc(META_DESC)
				.metaKey(META_KEY)
				.build();

		List<Post> posts = Arrays.asList(postOne, postTwo);
		when(postDaoMock.findAll()).thenReturn(posts);
		List<PostDTO> postDTOs = postService.getAll();
		assertEquals(2, postDTOs.size());

		int index = 0;
		for (PostDTO postDTO : postDTOs) {
			assertThat(postDTO.getId(), equalTo(posts.get(index).getId()));
			assertThat(postDTO.getCreatedAt(), equalTo(posts.get(index).getCreatedAt()));
			assertThat(postDTO.getTitle(), equalTo(posts.get(index).getTitle()));
			assertThat(postDTO.getAuthor(), equalTo(posts.get(index).getAuthor().getUsername()));
			assertThat(postDTO.getCategoryId(), equalTo(posts.get(index).getCategory().getId()));
			assertThat(postDTO.getIntro(), equalTo(posts.get(index).getIntro()));
			assertThat(postDTO.getContent(), equalTo(posts.get(index).getContent()));
			assertThat(postDTO.getMetaDesc(), equalTo(posts.get(index).getMetaDesc()));
			assertThat(postDTO.getMetaKey(), equalTo(posts.get(index).getMetaKey()));
			index++;
		}
	}
	
	@Test
	public void whenGetAllByUserShouldReturnOnePost() throws ServiceException, DaoException {
		List<Post> posts = Arrays.asList(postOne);
		when(postDaoMock.findAllByUser(USERNAME_ONE)).thenReturn(posts);
		List<PostDTO> postDTOs = postService.getAllByUser(USERNAME_ONE);
		assertEquals(1, postDTOs.size());

		assertThat(postDTOs.get(0).getId(), equalTo(postOne.getId()));
		assertThat(postDTOs.get(0).getCreatedAt(), equalTo(postOne.getCreatedAt()));
		assertThat(postDTOs.get(0).getTitle(), equalTo(postOne.getTitle()));
		assertThat(postDTOs.get(0).getAuthor(), equalTo(postOne.getAuthor().getUsername()));
		assertThat(postDTOs.get(0).getCategoryId(), equalTo(postOne.getCategory().getId()));
		assertThat(postDTOs.get(0).getIntro(), equalTo(postOne.getIntro()));
		assertThat(postDTOs.get(0).getContent(), equalTo(postOne.getContent()));
		assertThat(postDTOs.get(0).getMetaDesc(), equalTo(postOne.getMetaDesc()));
		assertThat(postDTOs.get(0).getMetaKey(), equalTo(postOne.getMetaKey()));
	}
}
