package net.yoedtos.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.dto.Page;
import net.yoedtos.blog.model.dto.PostDTO;
import net.yoedtos.blog.model.entity.Post;
import net.yoedtos.blog.repository.dao.PostDao;
import net.yoedtos.blog.util.Constants;

public class PagerService implements Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(PagerService.class);
	
	private PostDao postDao;
	
	public PagerService() {
		this.postDao = RepositoryFactory.create(PostDao.class);
	}
	
	public Page get(Long pageId) throws ServiceException {
		long begin;
		List<Post> posts;
		try {
			if(pageId == null) {
				begin = 0;
				pageId = 1L;
			} else {
				begin = (pageId - 1) * Constants.MAX_PAGE_SIZE; 
			}
			posts = postDao.getLatestBetween(begin, Constants.MAX_PAGE_SIZE);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return new Page.Builder()
				.previous(String.valueOf(pageId - 1))
				.current(String.valueOf(pageId))
				.next(String.valueOf(pageId + 1))
				.posts(posts.stream().map(PostDTO::convert).collect(Collectors.toList()))
				.build();
	}

	public String getPages() throws ServiceException {
		long pages;
		try {
			long total = postDao.getTotalPosts();
			pages = total / Constants.MAX_PAGE_SIZE;
			if(total % Constants.MAX_PAGE_SIZE != 0) {
				pages = pages + 1;
			}
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return String.valueOf(pages);
	}
}
