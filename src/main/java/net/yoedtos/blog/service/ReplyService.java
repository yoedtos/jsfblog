package net.yoedtos.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.dto.ReplyDTO;
import net.yoedtos.blog.model.entity.Comment;
import net.yoedtos.blog.model.entity.Reply;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.dao.CommentDao;
import net.yoedtos.blog.repository.dao.ReplyDao;
import net.yoedtos.blog.repository.dao.UserDao;

public class ReplyService extends AbstractService<ReplyDTO> implements Service{
	private static final Logger LOGGER = LoggerFactory.getLogger(ReplyService.class);
	
	private ReplyDao replyDao;
	private UserDao userDao;
	private CommentDao commentDao;
	
	public ReplyService() {
		super();
		this.replyDao = RepositoryFactory.create(ReplyDao.class);
		this.userDao = RepositoryFactory.create(UserDao.class);
		this.commentDao = RepositoryFactory.create(CommentDao.class);
	}

	@Override
	public void create(ReplyDTO replyDto) throws ServiceException {
		replyDto.setCreateAt(getToday());
		Reply reply = Reply.convert(replyDto);
		
		try {
			User user = userDao.findByUsername(replyDto.getAuthor());
			Comment comment = commentDao.findById(replyDto.getCommentId());
			reply.setComment(comment);
			reply.setAuthor(user);
			
			replyDao.persist(reply);
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
	public ReplyDTO update(ReplyDTO t) throws ServiceException {
		return null;
	}

	@Override
	public ReplyDTO get(Long id) throws ServiceException {
		return null;
	}

	@Override
	public List<ReplyDTO> getAll() throws ServiceException {
		return null;
	}

	public  List<ReplyDTO> getAllByComment(Long id) throws ServiceException {
		List<Reply> replies;
		try {
			replies = replyDao.findAllByCommentId(id);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return replies.stream()
				.map(ReplyDTO::convert)
				.collect(Collectors.toList());
	}
}
