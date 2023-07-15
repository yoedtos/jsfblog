package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.BEGIN_FOUR;
import static net.yoedtos.blog.util.TestConstants.BEGIN_NG;
import static net.yoedtos.blog.util.TestConstants.BEGIN_ZERO;
import static net.yoedtos.blog.util.TestConstants.CREATE_ONE;
import static net.yoedtos.blog.util.TestConstants.MAX_PAGES;
import static net.yoedtos.blog.util.TestConstants.PAGE_NG;
import static net.yoedtos.blog.util.TestConstants.PAGE_TWO;
import static net.yoedtos.blog.util.TestConstants.TOTAL_POSTS;
import static net.yoedtos.blog.util.TestUtil.createCategory;
import static net.yoedtos.blog.util.TestUtil.createDate;
import static net.yoedtos.blog.util.TestUtil.createPostDTO;
import static net.yoedtos.blog.util.TestUtil.createPostOne;
import static net.yoedtos.blog.util.TestUtil.createPostTwo;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static net.yoedtos.blog.util.TestUtil.createUserTwo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.model.dto.Page;
import net.yoedtos.blog.model.dto.PostDTO;
import net.yoedtos.blog.model.entity.Category;
import net.yoedtos.blog.model.entity.Post;
import net.yoedtos.blog.repository.dao.PostDao;

@RunWith(MockitoJUnitRunner.class)
public class PagerServiceTest {

	private final static String PREVIOUS = "Previous";
	private final static String CURRENT = "Current";
	private final static String NEXT = "Next";
	private final static String SIZE = "Size";
	private final static String PAGES = "Pages";
	private final static String ZERO = "0";
	private final static String ONE = "1";
	private final static String TWO = "2";
	private final static String THREE = "3";
	
	@Mock
	private PostDao postDaoMock;

	@InjectMocks
	private PagerService pagerService;

	private Date created;
	private Post postOne, postTwo;
	private PostDTO postDtoOne;
	private Category category;
	
	@Before
	public void init() {
		created = createDate(CREATE_ONE);
		category = createCategory();
		
		postOne = createPostOne(created, createUserOne(created), category);
		postTwo = createPostTwo(created, createUserTwo(created), category);
		
		postDtoOne = createPostDTO(created);
	}

	@Test
	public void whenGetPagesShouldReturnThree() throws DaoException, ServiceException {
		when(postDaoMock.getTotalPosts()).thenReturn(TOTAL_POSTS);
		
		String pages = pagerService.getPages();
		assertEquals(PAGES, THREE, pages);
		
		verify(postDaoMock, times(1)).getTotalPosts();
	}
	
	@Test
	public void whenGetPageIsNullShouldReturnPageOne() throws DaoException, ServiceException {
		when(postDaoMock.getLatestBetween(BEGIN_ZERO, MAX_PAGES)).thenReturn(Arrays.asList(postOne, postTwo));
		
		Page page = pagerService.get(null);
		assertEquals(PREVIOUS, ZERO, page.getPrevious());
		assertEquals(CURRENT, ONE, page.getCurrent());
		assertEquals(NEXT, TWO, page.getNext());
		assertEquals(SIZE, 2, page.getPosts().size());
		assertThat(page.getPosts(), hasItem(postDtoOne));

		verify(postDaoMock, times(1)).getLatestBetween(BEGIN_ZERO, MAX_PAGES);
	}

	@Test
	public void whenGetPageIsTwoShouldReturnPageTwo() throws DaoException, ServiceException {
		when(postDaoMock.getLatestBetween(BEGIN_FOUR, MAX_PAGES)).thenReturn(Arrays.asList(postOne, postTwo));
		
		Page page = pagerService.get(PAGE_TWO);
		assertEquals(PREVIOUS, ONE, page.getPrevious());
		assertEquals(CURRENT, TWO, page.getCurrent());
		assertEquals(NEXT, THREE, page.getNext());
		assertEquals(SIZE, 2, page.getPosts().size());
	
		verify(postDaoMock, times(1)).getLatestBetween(BEGIN_FOUR, MAX_PAGES);
	}
	
	@Test(expected = ServiceException.class)
	public void whenGetPageInvalidShouldThrowException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(postDaoMock).getLatestBetween(BEGIN_NG, MAX_PAGES);
		
		Page page = pagerService.get(PAGE_NG);
		assertNull(page);
		verify(postDaoMock, times(1)).getLatestBetween(BEGIN_NG, MAX_PAGES);
	}
}
