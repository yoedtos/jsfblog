package net.yoedtos.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.MailerException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.mail.Mailer;
import net.yoedtos.blog.model.dto.Login;
import net.yoedtos.blog.model.dto.Reset;
import net.yoedtos.blog.model.entity.Token;

public class ResetService implements Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResetService.class);
	
	private Mailer mailer;
	private CryptoHelper cryptoHelper;
	private SettingService settingService;
	private TokenService tokenService;
	
	public ResetService() throws ServiceException {
		settingService = ServiceFactory.create(SettingService.class);
		mailer = new Mailer(settingService.get());
		cryptoHelper = new CryptoHelper();
		tokenService = ServiceFactory.create(TokenService.class);
	}
	
	public void initiate(String username) throws ServiceException {
		try {
			Token token = tokenService.make(new Reset.Builder().username(username).build());
			mailer.createResetMail(token).send();
		} catch (ServiceException | MailerException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	public void complete(Login login, String token) throws ServiceException {
		try {
			String encoded = cryptoHelper.encrypt(login.getPassword());
			tokenService.expire(new Reset.Builder()
								.username(login.getUsername())
								.codedpass(encoded).token(token).build());
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
}
