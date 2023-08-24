package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.SEARCH_TITLE;
import static net.yoedtos.blog.util.TestUtil.createFoundOne;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.SearchException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.search.Found;
import net.yoedtos.blog.search.SearchEngine;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceTest {

	@Mock
	private SearchEngine searchEngine;
	
	@InjectMocks
	private SearchService searchService;
	
	private Found foundOne;
	
	@Before
	public void init() {
		foundOne = createFoundOne();
	}
	
	@Test
	public void whenSearchForTitleWordShouldHaveOneResult() throws ServiceException, SearchException {
		when(searchEngine.simpleSearch(SEARCH_TITLE)).thenReturn(Arrays.asList(foundOne));
		
		List<Found> founds = searchService.search(SEARCH_TITLE);
		assertEquals(1, founds.size());
		assertThat(founds, hasItems(foundOne));
	}
	
	@Test(expected = ServiceException.class)
	public void whenSearchForTitleWordFailShouldThrowsException() throws ServiceException, SearchException {
		doThrow(new SearchException()).when(searchEngine).simpleSearch(SEARCH_TITLE);
		
		searchService.search(SEARCH_TITLE);
		verify(searchEngine, times(1)).simpleSearch(SEARCH_TITLE);
	}
}
