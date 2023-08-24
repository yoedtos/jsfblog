package net.yoedtos.blog.search;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.AppException;
import net.yoedtos.blog.exception.SearchException;
import net.yoedtos.blog.model.entity.Post;
import net.yoedtos.blog.repository.dao.DaoHandler;

public class SearchEngine {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchEngine.class);
	
	private static final String[] FIELDS = {"title", "metaDesc", "metaKey"};
	private static final String[] PROJECTION = {"id", "title", "metaDesc"};
	
	private EntityManager entityManager;
	private Class<?> entity;
	
	public SearchEngine() {
		this.entity = Post.class;
	}
	
	public static void createIndex(EntityManager entityManager) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		try {
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
			throw new AppException(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<Found> simpleSearch(String keyword) throws SearchException {
		FullTextEntityManager fullTextEntityManager = getFullTextEntityManager(); 
		entityManager.getTransaction().begin();
		
		
		List<Found> results;
		try {
			Query query = fullTextEntityManager
								.getSearchFactory()
								.buildQueryBuilder()
								.forEntity(entity).get()
								.keyword()
								.onFields(FIELDS)
								.matching(keyword)
								.createQuery();
			
			results = fullTextEntityManager
									.createFullTextQuery(query, entity)
									.setProjection(PROJECTION)
									.setResultTransformer(new Found())
									.getResultList();
		} catch (Exception e) {
			entityManager.close();
			LOGGER.error(e.getMessage());
			throw new SearchException(e.getMessage());
		}
		
		entityManager.getTransaction().commit();
		entityManager.close();
		return results;
	}
	
	private FullTextEntityManager getFullTextEntityManager() {
		entityManager = DaoHandler.getEntityManager();
		return Search.getFullTextEntityManager(entityManager);
	}
}
