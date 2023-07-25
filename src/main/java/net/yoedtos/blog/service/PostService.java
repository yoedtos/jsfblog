package net.yoedtos.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.dto.PostDTO;
import net.yoedtos.blog.model.entity.Post;
import net.yoedtos.blog.repository.dao.CategoryDao;
import net.yoedtos.blog.repository.dao.PostDao;
import net.yoedtos.blog.repository.dao.UserDao;

public class PostService extends AbstractService<PostDTO> implements Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);
	
	private PostDao postDao;
	private CategoryDao categoryDao;
	private UserDao userDao;
	
	public PostService() {
		postDao = RepositoryFactory.create(PostDao.class);
		categoryDao = RepositoryFactory.create(CategoryDao.class);
		userDao = RepositoryFactory.create(UserDao.class);
	}

	@Override
	public void create(PostDTO postDto) throws ServiceException {
		postDto.setCreatedAt(getToday());
		Post post = Post.convert(postDto);
		try {
			post.setCategory(categoryDao.findById(postDto.getCategoryId()));
			post.setAuthor(userDao.findByUsername(postDto.getAuthor()));
			postDao.persist(post);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}	
	}

	@Override
	public void remove(Long id) throws ServiceException {
		try {
			postDao.remove(id);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public PostDTO update(PostDTO postDto) throws ServiceException {
		Post postDb;
		try {
			Post post = Post.convert(postDto);
			postDb = postDao.findById(postDto.getId());
			post.setAuthor(postDb.getAuthor());
			post.setCategory(postDb.getCategory());
			
			postDb = postDao.merge(post);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return PostDTO.convert(postDb);
	}

	@Override
	public PostDTO get(Long id) throws ServiceException {
		Post post;
		try {
			post = postDao.findById(id);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return PostDTO.convert(post);
	}

	@Override
	public List<PostDTO> getAll() throws ServiceException {
		List<Post> posts;
		try {
			posts = postDao.findAll();
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return posts.stream()
				.map(PostDTO::convert)
				.collect(Collectors.toList());
	}
	
}
