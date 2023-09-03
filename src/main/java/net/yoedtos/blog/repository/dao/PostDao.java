package net.yoedtos.blog.repository.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Category;
import net.yoedtos.blog.model.entity.Post;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class PostDao extends AbstractDao<Post> implements Repository<Post> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PostDao.class);
	
	public PostDao() {
		super(Post.class);
	}

	@Override
	public void persist(Post post) throws DaoException {
		try {
			save(post);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public void remove(Long id) throws DaoException {
		try {
			delete(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}		
	}

	@Override
	public Post merge(Post post) throws DaoException {
		Post postDb;
		try {
			 postDb = update(post);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return postDb;
	}

	@Override
	public Post findById(Long id) throws DaoException {
		Post postDb;
		try {
			postDb = loadById(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return postDb;
	}

	@Override
	public List<Post> findAll() throws DaoException {
		List<Post> posts;
		try {
			posts = loadByQuery("Post.loadAll");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return posts;
	}

	public List<Post> getLatestBetween(long begin, int maxPages) throws DaoException {
		List<Post> objPosts;
		try {
			objPosts = loadByQuery("Post.loadLastBetween", begin, maxPages);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return convertToPost(objPosts);
	}

	public Long getTotalPosts() throws DaoException {
		try {
			return (Long) loadOneByQuery("Post.loadPostTotal");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

	public List<Post> findAllByUser(String username) throws DaoException {
		try {
			return loadByQuery("Post.loadAllByUser", QueryKey.USERNAME, username);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	private List<Post> convertToPost(List<Post> objPosts) {
		List<Post> posts = new ArrayList<>();
		for (Iterator iterator = objPosts.iterator(); iterator.hasNext();) {
			Object[] arrayObj = (Object[]) iterator.next();
			java.sql.Timestamp sqlDate = (java.sql.Timestamp) arrayObj[1];

			posts.add(new Post.Builder()
					.id((Long)arrayObj[0])
					.createdAt(new Date(sqlDate.getTime()))
					.title((String) arrayObj[2])
					.author((User) arrayObj[3])
					.category(new Category())
					.intro((String) arrayObj[4])
					.content("")
					.metaDesc("")
					.metaKey("")
					.build());
		}
		return posts;
	}
}
