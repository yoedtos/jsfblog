package net.yoedtos.blog.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.model.dto.CategoryDTO;
import net.yoedtos.blog.model.entity.Category;
import net.yoedtos.blog.repository.dao.CategoryDao;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

	@Mock
	private CategoryDao categoryDaoMock;

	@InjectMocks
	private CategoryService categoryService;

	@Captor
	private ArgumentCaptor<?> captor;
	
	final static private long CATEGORY_ID = 1;
	final static private String CATEGORY_ONE= "One";
	
	private Category categoryOne;
	private CategoryDTO categoryDTO;
	
	@Before
	public void init() {
		categoryDTO = new CategoryDTO(CATEGORY_ONE);
		
		categoryOne = new Category();
		categoryOne.setId(CATEGORY_ID);
		categoryOne.setValue(CATEGORY_ONE);
	}

	@Test
	public void whenCreateShouldCreateOnce() throws ServiceException, DaoException {
		categoryService.create(categoryDTO);
		verify(categoryDaoMock).persist((Category) captor.capture());
		Category category = (Category) captor.getValue();
		assertEquals(CATEGORY_ONE, category.getValue());
		verify(categoryDaoMock, times(1)).persist(category);
	}
	
	@Test
	public void whenRemoveShouldRemoveOnce() throws DaoException, ServiceException {
		categoryService.remove(CATEGORY_ID);
		verify(categoryDaoMock).remove((Long) captor.capture());
		assertEquals(CATEGORY_ID, ((Long) captor.getValue()).intValue());
		verify(categoryDaoMock, times(1)).remove(CATEGORY_ID);
	}
	
	@Test(expected = ServiceException.class)
	public void whenCreateShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(categoryDaoMock).persist(categoryOne);
		categoryService.create(categoryDTO);
		verify(categoryDaoMock, times(1)).persist(categoryOne);
	}
	
	@Test(expected = ServiceException.class)
	public void whenRemoveShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(categoryDaoMock).remove(CATEGORY_ID);
		categoryService.remove(CATEGORY_ID);
		verify(categoryDaoMock, times(1)).remove(CATEGORY_ID);
	}
	
	@Test
	public void whenGetShouldReturnNull() throws ServiceException {
		CategoryDTO nullCategoryDTO = categoryService.get(CATEGORY_ID);
		assertNull(nullCategoryDTO);
	}
	
	@Test
	public void whenUpdateShouldReturnNull() throws ServiceException {
		CategoryDTO nullCategoryDTO = categoryService.update(categoryDTO);
		assertNull(nullCategoryDTO);
	}
	
	@Test(expected = ServiceException.class)
	public void whenGetAllShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(categoryDaoMock).findAll();
		categoryService.getAll();
		verify(categoryDaoMock, times(1)).findAll();
	}
	
	@Test
	public void whenGetAllShouldReturnTwoCategories() throws ServiceException, DaoException {
		Category categoryTwo = new Category();
		categoryTwo.setId(2L);
		categoryTwo.setValue("Two");
		
		List<Category> categories = Arrays.asList(categoryOne, categoryTwo);
		when(categoryDaoMock.findAll()).thenReturn(categories);
		List<CategoryDTO> categoryDTOs = categoryService.getAll();
		assertEquals(2, categoryDTOs.size());
		
		int index = 0;
		for (CategoryDTO categoryDTO : categoryDTOs) {
			assertThat(categoryDTO.getId(), is(categories.get(index).getId()));
			assertThat(categoryDTO.getValue(), is(categories.get(index).getValue()));
			index++;
		}
	}
}
