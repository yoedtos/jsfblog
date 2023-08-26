package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.SEARCH;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.search.Found;
import net.yoedtos.blog.service.SearchService;

@ManagedBean(name="search")
@RequestScoped
public class SearchBean extends AbstractBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchBean.class);
	
	private String keyword;
	private List<Found> results;
	private SearchService searchService;
	
	public SearchBean() {
		searchService = ServiceFactory.create(SearchService.class);
		results = new ArrayList<>();
	}
	
	public String doSearch() {
		try {
			results = searchService.search(keyword);
			return SEARCH;
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
		}
		return "";
	}

	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<Found> getResults() {
		return results;
	}
}
