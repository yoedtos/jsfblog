package net.yoedtos.blog.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.SearchException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.search.Found;
import net.yoedtos.blog.search.SearchEngine;

public class SearchService implements Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);
	
	private SearchEngine engine;
	
	public SearchService() {
		this.engine = new SearchEngine();
	}
	
	public List<Found> search(String keyword) throws ServiceException {
		try {
			return engine.simpleSearch(keyword);
		} catch (SearchException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
}
