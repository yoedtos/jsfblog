package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.CATEGORY_ONE;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_TWO;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_TWO_ID;
import static org.hamcrest.CoreMatchers.equalTo;
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

	private Category categoryOne;
	private CategoryDTO categoryDTO;
	
	@Before
	public void init() {
		categoryDTO = new CategoryDTO(CATEGORY_ONE);
		
		categoryOne = new Category();
		categoryOne.setId(CATEGORY_ONE_ID);
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
		categoryService.remove(CATEGORY_ONE_ID);
		verify(categoryDaoMock).remove((Long) captor.capture());
		assertEquals(CATEGORY_ONE_ID, ((Long) captor.getValue()).intValue());
		verify(categoryDaoMock, times(1)).remove(CATEGORY_ONE_ID);
	}
	
	@Test(expected = ServiceException.class)
	public void whenCreateShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(categoryDaoMock).persist(categoryOne);
		categoryService.create(categoryDTO);
		verify(categoryDaoMock, times(1)).persist(categoryOne);
	}
	
	@Test(expected = ServiceException.class)
	public void whenRemoveShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(categoryDaoMock).remove(CATEGORY_ONE_ID);
		categoryService.remove(CATEGORY_ONE_ID);
		verify(categoryDaoMock, times(1)).remove(CATEGORY_ONE_ID);
	}
	
	@Test
	public void whenGetShouldReturnNull() throws ServiceException {
		CategoryDTO nullCategoryDTO = categoryService.get(CATEGORY_ONE_ID);
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
		categoryTwo.setId(CATEGORY_TWO_ID);
		categoryTwo.setValue(CATEGORY_TWO);
		
		List<Category> categories = Arrays.asList(categoryOne, categoryTwo);
		when(categoryDaoMock.findAll()).thenReturn(categories);
		List<CategoryDTO> categoryDTOs = categoryService.getAll();
		assertEquals(2, categoryDTOs.size());
		
		int index = 0;
		for (CategoryDTO categoryDTO : categoryDTOs) {
			assertThat(categoryDTO.getId(), equalTo(categories.get(index).getId()));
			assertThat(categoryDTO.getValue(), equalTo(categories.get(index).getValue()));
			index++;
		}
	}
}
