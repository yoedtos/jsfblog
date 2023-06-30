package net.yoedtos.blog.model.dto;

import java.util.Objects;
import java.util.Properties;

import net.yoedtos.blog.repository.fs.PropsKey;
import net.yoedtos.blog.view.i18n.Language;

public class SettingDTO {

	private String siteName;
	private String siteAddress;
	private String siteMetaKey;
	private String siteMetaDesc;
	private String siteEmail;
	private String smtpServer;
	private String smtpPort;
	private boolean useTLS;
	private String smtpUser;
	private String smtpPassword;
	private Language language;
	
	private SettingDTO(Builder builder) {
		this.siteName = builder.siteName;
		this.siteAddress = builder.siteAddress;
		this.siteMetaKey = builder.siteMetaKey;
		this.siteMetaDesc = builder.siteMetaDesc;
		this.siteEmail = builder.siteEmail;
		this.smtpServer = builder.smtpServer;
		this.smtpPort = builder.smtpPort;
		this.useTLS = builder.useTLS;
		this.smtpUser = builder.smtpUser;
		this.smtpPassword = builder.smtpPassword;
		this.language = builder.language;
	}
	
	public String getSiteName() {
		return siteName;
	}
	
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	public String getSiteAddress() {
		return siteAddress;
	}
	
	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}
	
	public String getSiteMetaKey() {
		return siteMetaKey;
	}
	
	public void setSiteMetaKey(String siteMetaKey) {
		this.siteMetaKey = siteMetaKey;
	}
	
	public String getSiteMetaDesc() {
		return siteMetaDesc;
	}
	
	public void setSiteMetaDesc(String siteMetaDesc) {
		this.siteMetaDesc = siteMetaDesc;
	}
	
	public String getSiteEmail() {
		return siteEmail;
	}
	
	public void setSiteEmail(String siteEmail) {
		this.siteEmail = siteEmail;
	}
	
	public String getSmtpServer() {
		return smtpServer;
	}
	
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}
	
	public String getSmtpPort() {
		return smtpPort;
	}
	
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}
	
	public boolean isUseTLS() {
		return useTLS;
	}
	
	public void setUseTLS(boolean useTLS) {
		this.useTLS = useTLS;
	}
	
	public String getSmtpUser() {
		return smtpUser;
	}
	
	public void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}
	
	public String getSmtpPassword() {
		return smtpPassword;
	}
	
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}
	
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public static SettingDTO fromProperties(Properties settings) {
		return new SettingDTO.Builder()
				.language(Language.valueOf((String) settings.get(PropsKey.LANGUAGE)))
				.siteName((String) settings.get(PropsKey.SITE_NAME))
				.siteAddress((String) settings.get(PropsKey.SITE_ADDRESS))
				.siteEmail((String) settings.get(PropsKey.SITE_EMAIL))
				.siteMetaDesc((String) settings.get(PropsKey.SITE_META_DESC))
				.siteMetaKey((String) settings.get(PropsKey.SITE_META_KEY))
				.smtpServer((String) settings.get(PropsKey.SMTP_SERVER))
				.smtpPort((String) settings.get(PropsKey.SMTP_PORT))
				.useTLS(Boolean.parseBoolean((String) settings.get(PropsKey.SMTP_USE_TLS)))
				.smtpUser((String) settings.get(PropsKey.SMTP_USER))
				.build();
	}
	
	public static Properties toProperties(SettingDTO settingDTO) {
		Properties settings = new Properties();
		settings.setProperty(PropsKey.LANGUAGE, settingDTO.getLanguage().name());
		settings.setProperty(PropsKey.SITE_NAME, settingDTO.getSiteName());
		settings.setProperty(PropsKey.SITE_ADDRESS, settingDTO.getSiteAddress());
		settings.setProperty(PropsKey.SITE_EMAIL, settingDTO.getSiteEmail());
		settings.setProperty(PropsKey.SITE_META_DESC, settingDTO.getSiteMetaDesc());
		settings.setProperty(PropsKey.SITE_META_KEY, settingDTO.getSiteMetaKey());
		settings.setProperty(PropsKey.SMTP_SERVER, settingDTO.getSmtpServer());
		settings.setProperty(PropsKey.SMTP_PORT, settingDTO.getSmtpPort());
		settings.setProperty(PropsKey.SMTP_USE_TLS, Boolean.toString(settingDTO.isUseTLS()));
		settings.setProperty(PropsKey.SMTP_USER, settingDTO.getSmtpUser());
		
		return settings;
	}
	
	public static class Builder {
		private String siteName;
		private String siteAddress;
		private String siteMetaKey;
		private String siteMetaDesc;
		private String siteEmail;
		private String smtpServer;
		private String smtpPort;
		private boolean useTLS;
		private String smtpUser;
		private String smtpPassword;
		private Language language;
		
		public Builder siteName(String siteName) {
			this.siteName = siteName;
			return this;
		}		
		
		public Builder siteAddress(String siteAddress) {
			this.siteAddress = siteAddress;
			return this;
		}
		
		public Builder siteMetaKey(String siteMetaKey) {
			this.siteMetaKey = siteMetaKey;
			return this;
		}
		
		public Builder siteMetaDesc(String siteMetaDesc) {
			this.siteMetaDesc = siteMetaDesc;
			return this;
		}
		
		public Builder siteEmail(String siteEmail) {
			this.siteEmail = siteEmail;
			return this;
		}
		
		public Builder smtpServer(String smtpServer) {
			this.smtpServer = smtpServer;
			return this;
		}
		
		public Builder smtpPort(String smtpPort) {
			this.smtpPort = smtpPort;
			return this;
		}
		
		public Builder useTLS(boolean useTLS) {
			this.useTLS = useTLS;
			return this;
		}
		
		public Builder smtpUser(String smtpUser) {
			this.smtpUser = smtpUser;
			return this;
		}
		
		public Builder smtpPassword(String smtpPassword) {
			this.smtpPassword = smtpPassword;
			return this;
		}
		
		public Builder language(Language language) {
			this.language = language;
			return this;
		}
		
		public SettingDTO build() {
			return new SettingDTO(this);
		}
	}
	
	@Override
	public String toString() {
		return "SettingDTO [siteName=" + siteName + ", siteAddress=" + siteAddress + ", siteMetaKey=" + siteMetaKey
				+ ", siteMetaDesc=" + siteMetaDesc + ", siteEmail=" + siteEmail + ", smtpServer=" + smtpServer
				+ ", smtpPort=" + smtpPort + ", useTLS=" + useTLS + ", smtpUser=" + smtpUser + ", smtpPassword="
				+ smtpPassword + ", language=" + language + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(language, siteAddress, siteEmail, siteMetaDesc, siteMetaKey, siteName, smtpPassword,
				smtpPort, smtpServer, smtpUser, useTLS);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SettingDTO other = (SettingDTO) obj;
		return language == other.language && Objects.equals(siteAddress, other.siteAddress)
				&& Objects.equals(siteEmail, other.siteEmail) && Objects.equals(siteMetaDesc, other.siteMetaDesc)
				&& Objects.equals(siteMetaKey, other.siteMetaKey) && Objects.equals(siteName, other.siteName)
				&& Objects.equals(smtpPassword, other.smtpPassword) && Objects.equals(smtpPort, other.smtpPort)
				&& Objects.equals(smtpServer, other.smtpServer) && Objects.equals(smtpUser, other.smtpUser)
				&& useTLS == other.useTLS;
	}
}
