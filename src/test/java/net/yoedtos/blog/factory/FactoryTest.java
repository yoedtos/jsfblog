package net.yoedtos.blog.factory;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import net.yoedtos.blog.repository.Repository;
import net.yoedtos.blog.repository.dao.CategoryDao;
import net.yoedtos.blog.service.CategoryService;
import net.yoedtos.blog.service.Service;

public class FactoryTest {
	
	@Test
	public void serviceCreationTest() {
		CategoryService categoryService = ServiceFactory.create(CategoryService.class);
		assertThat(categoryService, instanceOf(CategoryService.class));
		assertThat(categoryService, instanceOf(Service.class));
	}
	
	@Test
	public void repositoryCreationTest() {
		CategoryDao categoryDao = RepositoryFactory.create(CategoryDao.class);
		assertThat(categoryDao, instanceOf(CategoryDao.class));
		assertThat(categoryDao, instanceOf(Repository.class));
	}
}
