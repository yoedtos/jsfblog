package net.yoedtos.blog.mail;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.MailerException;
import net.yoedtos.blog.model.dto.SettingDTO;
import net.yoedtos.blog.model.entity.Token;
import net.yoedtos.blog.util.Constants;

public class Mailer {
	private static final Logger LOGGER = LoggerFactory.getLogger(Mailer.class);
	
	private static final String ROOT_RSRC = "i18n.text";
	private static final String RSRC_SUBJECT = "msg.reset_subject";
	private static final String RSRC_REQUIRE = "msg.reset_require";
	private static final String RSRC_BODY = "msg.reset_body";
	private static final String USER_PH = "#reset.user";
	private static final String LINK_PH = "#reset.link";
	private static final String NOREPLY = "noreply@";
	
	private SettingDTO setting;
	private ResourceBundle bundle;
	private HtmlEmail email;
	
	public Mailer(SettingDTO setting) {
		this.setting = setting;
		bundle = ResourceBundle.getBundle(ROOT_RSRC, new Locale(setting.getLanguage().toString()));
	}
	
	public Mailer createResetMail(Token token) throws MailerException {	
		String sender = NOREPLY + setting.getSiteEmail().split("@")[1];
		
		email = new HtmlEmail();
		try {
			email.addTo(token.getCreator().getEmail(), token.getCreator().getFullname())
				.setFrom(sender, setting.getSiteName())
				.setSubject(bundle.getString(RSRC_SUBJECT))
				.setAuthentication(setting.getSmtpUser(), setting.getSmtpPassword());
			email.setHtmlMsg(createResetContent(token));
			email.setTextMsg(bundle.getString(RSRC_REQUIRE));
		} catch (EmailException e) {
			LOGGER.error(e.getMessage());
			throw new MailerException(e.getMessage());
		}
		
		return this;
	}
	
	public String send() throws MailerException {	 
		String result;
		try {
			email.setHostName(setting.getSmtpServer());
			email.setSmtpPort(Integer.parseInt(setting.getSmtpPort()));
			email.setTLS(setting.isUseTLS());
			result = email.send();
		} catch (EmailException e) {
			LOGGER.error(e.getMessage());
			throw new MailerException(e.getMessage());
		}
		
		return result;
	}

	private String createResetContent(Token token) {	
		String link = setting.getSiteAddress() + Constants.RESET_URI_PARAM + token.getValue();
		String resource = bundle.getString(RSRC_BODY);
		
		String message = resource.replaceAll(LINK_PH, link)
									.replaceAll(USER_PH, token.getCreator().getFullname());
		return new StringBuilder()
				.append("<html>")
				.append(message)
				.append("</html").toString();
	}
}