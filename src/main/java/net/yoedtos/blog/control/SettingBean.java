package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.ADMIN;
import static net.yoedtos.blog.control.Constants.ADMIN_REDIRECT;

import java.io.IOException;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.dto.SettingDTO;
import net.yoedtos.blog.service.SettingService;
import net.yoedtos.blog.view.i18n.Language;

@ManagedBean(name="setting", eager=true)
@ApplicationScoped
public class SettingBean extends AbstractBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(SettingBean.class);
			
	private Locale locale;
	private SettingDTO dto;
	private SettingService settingService;
	
	public SettingBean() {
		super();
		settingService = ServiceFactory.create(SettingService.class);
	}

	@PostConstruct
	public void load() {
		try { 
			dto = settingService.get();
		} catch (ServiceException e) {
			dto = new SettingDTO.Builder().language(Language.EN).build();
			LOGGER.error(e.getMessage());
		}
		locale = new Locale(dto.getLanguage().toString());
	}
	
	public void onSetLanguage(ValueChangeEvent event) {
		if (event != null) {
			locale = new Locale(((Language) event.getNewValue()).toString());
		}
	}
	
	public String save() {
		try {
			settingService.create(dto);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
			return null;
		}
		showSuccessMessage(true);
		return ADMIN_REDIRECT;
	}
	
	public void cancel() {
		try {
			redirect(ADMIN);
		} catch (IOException e) {
			showErrorMessage(false);
		}
	}
	
	public Language[] getLanguages() {
		return Language.values();
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public SettingDTO getDto() {
		return dto;
	}
	
	public void setDto(SettingDTO dto) {
		this.dto = dto;
	}
}	
