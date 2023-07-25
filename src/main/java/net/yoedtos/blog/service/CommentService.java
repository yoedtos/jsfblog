package net.yoedtos.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.dto.CommentDTO;
import net.yoedtos.blog.model.entity.Comment;
import net.yoedtos.blog.repository.dao.CommentDao;
import net.yoedtos.blog.repository.dao.PostDao;

public class CommentService extends AbstractService<CommentDTO> implements Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);
	
	private CommentDao commentDao;
	private PostDao postDao;

	public CommentService() {
		commentDao = RepositoryFactory.create(CommentDao.class);
		postDao = RepositoryFactory.create(PostDao.class);
	}
	
	@Override
	public void create(CommentDTO commentDto) throws ServiceException {
		commentDto.setCreateAt(getToday());
		try {
			Comment comment = Comment.convert(commentDto);
			comment.setPost(postDao.findById(commentDto.getPostId()));
			commentDao.persist(comment);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void remove(Long id) throws ServiceException {
		throw new ServiceException("Not implemented");
	}

	@Override
	public CommentDTO update(CommentDTO commentDTO) throws ServiceException {
		return null;
	}

	@Override
	public CommentDTO get(Long id) throws ServiceException {
		return null;
	}

	@Override
	public List<CommentDTO> getAll() throws ServiceException {
		List<Comment> comments;
		try {
			comments = commentDao.findAll();
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return comments.stream()
				.map(CommentDTO::convert)
				.collect(Collectors.toList());
	}
	 
}
