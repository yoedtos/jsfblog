package net.yoedtos.blog.repository.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Post;
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
		// TODO Auto-generated method stub
		return null;
	}

	public Long getTotalPosts() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}
}
