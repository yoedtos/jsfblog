package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.ENC_UPDATE;
import static net.yoedtos.blog.util.TestConstants.PASS_UPDATE;
import static net.yoedtos.blog.util.TestConstants.TOKEN_ONE;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestConstants.USER_UNKNOWN;
import static net.yoedtos.blog.util.TestUtil.createResetOneExpire;
import static net.yoedtos.blog.util.TestUtil.createResetOneMake;
import static net.yoedtos.blog.util.TestUtil.createTokenOne;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.MailerException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.mail.Mailer;
import net.yoedtos.blog.model.dto.Login;
import net.yoedtos.blog.model.dto.Reset;
import net.yoedtos.blog.model.entity.Token;

@RunWith(MockitoJUnitRunner.class)
public class ResetServiceTest {
	
	@Mock
	private Mailer mailerMock;
	
	@Mock 
	private CryptoHelper cryptoHpMock;
	
	@Mock
	private TokenService tokenSrvMock;
	
	@InjectMocks
	private ResetService resetService;
	
	@Captor
	private ArgumentCaptor<?> captor;

	private Token token;
	private Login loginOne;
	private Reset resetExpire;
	
	@Before
	public void init() {
		resetExpire = createResetOneExpire();
		token = createTokenOne(new Date(), null);
		loginOne = new Login(USERNAME_ONE, PASS_UPDATE);
	}
	
	@Test
	public void whenResetInitiateShouldSendEmail() throws DaoException, ServiceException, MailerException {
		when(tokenSrvMock.make(createResetOneMake())).thenReturn(token);	
		when(mailerMock.createResetMail(token)).thenReturn(mailerMock);
		when(mailerMock.send()).thenReturn(anyString());
		
		resetService.initiate(USERNAME_ONE);
		
		verify(mailerMock).createResetMail((Token) captor.capture());
		Token captured = (Token) captor.getValue();
		assertThat(captured, equalTo(token));
		
		verify(mailerMock, times(1)).createResetMail(token);
		verify(mailerMock, times(1)).send();
	}
	
	@Test(expected = ServiceException.class)
	public void whenResetWithWrongUsernameShouldThrowException() throws ServiceException, MailerException {
		Reset unknown = new Reset.Builder().username(USER_UNKNOWN).build();
		
		when(tokenSrvMock.make(unknown)).thenThrow(new ServiceException());
		when(mailerMock.createResetMail(token)).thenReturn(mailerMock);
		when(mailerMock.send()).thenReturn(anyString());
		
		resetService.initiate(USER_UNKNOWN);
		
		verify(mailerMock, never()).createResetMail(token);
		verify(mailerMock, never()).send();
	}
	
	@Test(expected = ServiceException.class)
	public void whenResetWithEmailErrorShouldThrowException() throws ServiceException, MailerException {
		when(tokenSrvMock.make(createResetOneMake())).thenReturn(token);	
		when(mailerMock.createResetMail(token)).thenReturn(mailerMock);
		when(mailerMock.send()).thenThrow(new MailerException());
		
		resetService.initiate(USERNAME_ONE);
	}
	
	@Test
	public void whenResetCompleteSuccessShouldDoOnce() throws DaoException, ServiceException {
		when(cryptoHpMock.encrypt(PASS_UPDATE)).thenReturn(ENC_UPDATE);
		
		resetService.complete(loginOne, TOKEN_ONE);
		
		verify(tokenSrvMock).expire((Reset) captor.capture());
		Reset resetCaptured = (Reset) captor.getValue();
		
		assertThat(resetExpire, equalTo(resetCaptured));
		verify(cryptoHpMock, times(1)).encrypt(PASS_UPDATE);
		verify(tokenSrvMock, times(1)).expire(resetExpire);
	}
	
	@Test(expected = ServiceException.class)
	public void whenResetCompleteFailedShouldThrowException() throws DaoException, ServiceException {
		when(cryptoHpMock.encrypt(PASS_UPDATE)).thenReturn(ENC_UPDATE);
		doThrow(new ServiceException()).when(tokenSrvMock).expire(resetExpire);
		
		resetService.complete(loginOne, TOKEN_ONE);
			
		verify(tokenSrvMock, times(1)).expire(resetExpire);
	}
}
