package net.yoedtos.blog.search;

import static net.yoedtos.blog.util.TestConstants.CONTENT_NEW;
import static net.yoedtos.blog.util.TestConstants.INTRO_NEW;
import static net.yoedtos.blog.util.TestConstants.META_DESC;
import static net.yoedtos.blog.util.TestConstants.META_KEY;
import static net.yoedtos.blog.util.TestConstants.POST_NEW_ID;
import static net.yoedtos.blog.util.TestConstants.SEARCH_META;
import static net.yoedtos.blog.util.TestConstants.SEARCH_TITLE;
import static net.yoedtos.blog.util.TestConstants.SEARCH_TITLE_NEW;
import static net.yoedtos.blog.util.TestConstants.TITLE_NEW;
import static net.yoedtos.blog.util.TestUtil.createCategory;
import static net.yoedtos.blog.util.TestUtil.createFoundOne;
import static net.yoedtos.blog.util.TestUtil.createFoundTwo;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.yoedtos.blog.exception.SearchException;
import net.yoedtos.blog.model.entity.Post;
import net.yoedtos.blog.repository.dao.AbstractDaoTest;
import net.yoedtos.blog.repository.dao.DaoHandler;

public class SearchEngineTest extends AbstractDaoTest {

	private EntityManager entityManager;
	private SearchEngine searchEngine;
	private Found foundOne;
	
	public SearchEngineTest() throws DataSetException {
		super();
		foundOne = createFoundOne();
	}
	
	@Before
	public void init() throws DatabaseUnitException, SQLException {
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		entityManager = DaoHandler.getEntityManager();
		SearchEngine.createIndex(entityManager);
		this.searchEngine = new SearchEngine();
	}
	
	@After
	public void clean() {
		entityManager.close();
	}
	
	@Test
	public void whenSearchMetaWordShouldHaveTwoResults() throws SearchException {
		Found foundTwo = createFoundTwo();
		
		List<Found> founds = searchEngine.simpleSearch(SEARCH_META);
		assertEquals(2, founds.size());
		assertThat(founds, hasItems(foundOne, foundTwo));
	}
	
	@Test
	public void whenSearchTitleWordShouldHaveOneResult() throws SearchException {
		List<Found> founds = searchEngine.simpleSearch(SEARCH_TITLE);
		
		assertEquals(1, founds.size());
		assertThat(founds, hasItems(foundOne));
	}
	
	@Test
	public void whenPostThenSearchTitleWordShouldHaveOneResult() throws SearchException {
		Date today = new Date();
		Post postNew = new Post.Builder()
				.id(null)
				.createdAt(today)
				.title(TITLE_NEW)
				.author(createUserOne(today))
				.category(createCategory())
				.intro(INTRO_NEW)
				.content(CONTENT_NEW)
				.metaDesc(META_DESC)
				.metaKey(META_KEY)
				.build();
		addPostData(postNew);
		
		List<Found> founds = searchEngine.simpleSearch(SEARCH_TITLE_NEW);
		
		assertEquals(1, founds.size());
		assertThat(founds.get(0).getId(), equalTo(POST_NEW_ID));
		assertThat(founds.get(0).getTitle(), equalTo(TITLE_NEW));
		assertThat(founds.get(0).getDetails(), equalTo(META_DESC));
	}
	
	private void addPostData(Post post) {
		entityManager.getTransaction().begin();
		entityManager.persist(post);
		entityManager.getTransaction().commit();
	}
}
