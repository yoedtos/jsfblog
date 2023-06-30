package net.yoedtos.blog.repository.fs;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.FSException;
import net.yoedtos.blog.util.TestUtil;

@RunWith(MockitoJUnitRunner.class)
public class SettingPropsTest {

	@Mock
	private PropertiesHelper propsHelperMock;
	
	@InjectMocks
	private SettingProps settingProps;
	
	private Properties settings;

	@Before
	public void init() {
		settings = TestUtil.createSettings();
	}
	
	@Test(expected = FSException.class)
	public void whenStoreFailShouldThrowException() throws FSException, IOException {
		doThrow(new IOException()).when(propsHelperMock).write(settings);
		settingProps.store(settings);
		verify(propsHelperMock, times(1)).write(settings);
	}
	 
	@Test
	public void whenStoreShouldHavePropertySetting() throws FSException, IOException {
		settingProps.store(settings);
		verify(propsHelperMock, times(1)).write(settings);
	}
	
	@Test
	public void whenLoadShouldReturnPropertySetting() throws FSException, IOException {
		when(propsHelperMock.read()).thenReturn(settings);
		Properties settingProperties = settingProps.load(null);
		assertThat(settingProperties, equalTo(settings));
		verify(propsHelperMock, times(1)).read();
	}
	
	@Test(expected = FSException.class)
	public void whenDropShouldThrowException() throws FSException {
		settingProps.drop(settings);
	}
	
	@Test
	public void whenReplaceShouldReturnNull() throws FSException {
		Properties nullSettings = settingProps.replace(settings);
		assertNull(nullSettings);
	}
	
	@Test
	public void whenFindShouldReturnNull() throws FSException {
		Object nullSetting = settingProps.find("Anything");
		assertNull(nullSetting);
	}
}
