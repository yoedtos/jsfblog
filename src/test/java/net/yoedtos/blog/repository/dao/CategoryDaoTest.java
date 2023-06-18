package net.yoedtos.blog.repository.dao;

import static net.yoedtos.blog.util.TestConstants.CATEGORY_NEW;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_NEW_ID;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_TWO;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_TWO_ID;
import static net.yoedtos.blog.util.TestUtil.createCategory;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.entity.Category;

public class CategoryDaoTest extends AbstractDaoTest {
	
	private CategoryDao categoryDao;
	private Category categoryOne;
	
	public CategoryDaoTest() throws DatabaseUnitException, SQLException {
		super();
		categoryDao = RepositoryFactory.create(CategoryDao.class);
		categoryOne = createCategory();
	}

	@Before
	public void init() throws DatabaseUnitException, SQLException {
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
	
	@Test
	public void whenFindAllShouldReturnTwoCategories() throws DaoException {
		Category categoryTwo = new Category();
		categoryTwo.setId(CATEGORY_TWO_ID);
		categoryTwo.setValue(CATEGORY_TWO);
		
		List<Category> categories = categoryDao.findAll();
		assertThat(categories.size(), equalTo(2));
		assertThat(categories, hasItems(new Category[] {categoryOne, categoryTwo}));
	}
	
	@Test(expected = DaoException.class)
	public void whenAddCategoryTwiceShouldThrowException() throws DaoException {
		categoryDao.persist(categoryOne);
	}
	 
	@Test
	public void whenAddCategoryShouldHaveOne() throws DaoException {
		Category category = new Category();
		category.setValue(CATEGORY_NEW);
		
		categoryDao.persist(category);
		
		Category categoryDb = categoryDao.findById(CATEGORY_NEW_ID);
		assertThat(categoryDb.getId(), equalTo(CATEGORY_NEW_ID));
		assertThat(categoryDb.getValue(), equalTo(CATEGORY_NEW));
	}
}
