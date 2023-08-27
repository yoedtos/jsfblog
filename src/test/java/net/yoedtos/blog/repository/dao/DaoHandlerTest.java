package net.yoedtos.blog.repository.dao;

import static org.junit.Assert.assertNotNull;
import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DaoHandlerTest {

	@Before
	public void setup() {
		DaoHandler.init();
	}
	
	@After
	public void tearDown() {
		DaoHandler.shutdown();
	}
	
	@Test
	public void whenInitShouldHaveEntityManager() {
		EntityManager entityManager = DaoHandler.getEntityManager();
		assertNotNull(entityManager);
	}
}
