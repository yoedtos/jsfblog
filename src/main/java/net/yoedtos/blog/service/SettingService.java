package net.yoedtos.blog.service;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.FSException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.StorageFactory;
import net.yoedtos.blog.model.dto.SettingDTO;
import net.yoedtos.blog.repository.fs.PropsKey;
import net.yoedtos.blog.repository.fs.SettingProps;
import net.yoedtos.blog.util.Constants;

public class SettingService implements Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(SettingService.class);
	
	private SettingProps settingProps;
	
	public SettingService() {
		super();
		this.settingProps = StorageFactory.create(SettingProps.class);
	}

	public void create(SettingDTO settingDto) throws ServiceException {
		Properties settings = SettingDTO.toProperties(settingDto);
		try {
			settings.setProperty(PropsKey.SMTP_PASSWORD,settingDto.getSmtpPassword());
			settingProps.store(settings);
		} catch (FSException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	public SettingDTO update(SettingDTO settingDto) throws ServiceException {
		Properties settings = SettingDTO.toProperties(settingDto);
		try {
			if(!settingDto.getSmtpPassword().equals(Constants.PASS_MASK)) {
				settings.setProperty(PropsKey.SMTP_PASSWORD, settingDto.getSmtpPassword());
			}
			settings = settingProps.replace(settings);
		} catch (FSException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		SettingDTO updatedSettingDto = SettingDTO.fromProperties(settings);
		updatedSettingDto.setSmtpPassword(Constants.PASS_MASK);
		
		return updatedSettingDto;
	}

	public SettingDTO get() throws ServiceException {
		SettingDTO settingDTO;
		try {
			Properties settings = settingProps.load(null);
			settingDTO = SettingDTO.fromProperties(settings);
			settingDTO.setSmtpPassword(Constants.PASS_MASK);
		} catch (FSException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return settingDTO;
	}
}
