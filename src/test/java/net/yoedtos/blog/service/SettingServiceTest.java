package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.SMTP_PASSWORD_UPDATE;
import static net.yoedtos.blog.util.TestUtil.createSettingDTO;
import static net.yoedtos.blog.util.TestUtil.createSettings;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.FSException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.model.dto.SettingDTO;
import net.yoedtos.blog.repository.fs.PropsKey;
import net.yoedtos.blog.repository.fs.SettingProps;
import net.yoedtos.blog.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class SettingServiceTest {
	
	@Mock
	private SettingProps settingPropsMock;
	
	@InjectMocks
	private SettingService settingService;

	@Captor
	private ArgumentCaptor<Properties> captor;
	
	private SettingDTO settingDTO;
	private Properties settings;
	
	@Before
	public void init() {
		settingDTO = createSettingDTO();
		settings = createSettings();
	}

	@Test
	public void whenCreateShouldCorrectCreate() throws ServiceException, FSException {
		settingService.create(settingDTO);
		verify(settingPropsMock).store(captor.capture());
		Properties savedSettings = captor.getValue();
		assertThat(savedSettings, equalTo(settings));
		verify(settingPropsMock, times(1)).store(settings);
	}
	
	@Test(expected = ServiceException.class)
	public void whenCreateFailShouldThrowsException() throws ServiceException, FSException {
		doThrow(new FSException()).when(settingPropsMock).store(settings);
		settingService.create(settingDTO);
		verify(settingPropsMock, times(1)).store(settings);
	}
	
	@Test
	public void whenGetShouldReturnSettings() throws ServiceException, FSException {
		when(settingPropsMock.load(null)).thenReturn(settings);
		SettingDTO settings = settingService.get();
		assertThat(settings, equalTo(getExpected()));
	}
	
	@Test
	public void whenUpdateShouldReturnSettings() throws ServiceException, FSException {
		SettingDTO settingDtoUpdate = settingDTO;
		settingDtoUpdate.setSmtpPassword(SMTP_PASSWORD_UPDATE);
		Properties settingsUpdate = settings;
		settingsUpdate.setProperty(PropsKey.SMTP_PASSWORD, SMTP_PASSWORD_UPDATE);
		
		when(settingPropsMock.replace(settingsUpdate)).thenReturn(settingsUpdate);
		SettingDTO updatedSettings = settingService.update(settingDtoUpdate);
		verify(settingPropsMock, times(1)).replace(settingsUpdate);
		assertThat(updatedSettings, equalTo(getExpected()));
	}
	
	private SettingDTO getExpected() {
		settingDTO.setSmtpPassword(Constants.PASS_MASK);
		return settingDTO;
	}
}
