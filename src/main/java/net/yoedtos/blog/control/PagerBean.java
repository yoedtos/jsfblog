package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.FIRST_PG;
import static net.yoedtos.blog.control.Constants.NOT_FOUND;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.AppException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.dto.Page;
import net.yoedtos.blog.service.PagerService;

@ManagedBean(name="pager")
@SessionScoped
public class PagerBean extends AbstractBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(PagerBean.class);
	
	private Page dto;
	private PagerService pagerService;
	
	public PagerBean() {
		super();
		pagerService = ServiceFactory.create(PagerService.class);
		dto = new Page.Builder().build();
	}

	public void loadPage() {
		try {
			if (dto.getPageId() == null) 
				dto = pagerService.get(null);
			else
				dto = pagerService.get(Long.parseLong(dto.getPageId()));
			dto.setFirst(FIRST_PG);
			dto.setLast(pagerService.getPages());
		} catch (NumberFormatException | ServiceException e) {
			LOGGER.error(e.getMessage());
			try {
				redirect(NOT_FOUND);
			} catch (IOException ex) {
				throw new AppException(ex.getMessage());
			}
		}
	}
	
	public Page getDto() {
		return dto;
	}

	public void setDto(Page dto) {
		this.dto = dto;
	}
}
