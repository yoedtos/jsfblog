package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.CONTENT_TYPE_ONE;
import static net.yoedtos.blog.util.TestConstants.CONTENT_TYPE_TWO;
import static net.yoedtos.blog.util.TestConstants.CREATE_ONE;
import static net.yoedtos.blog.util.TestConstants.CREATE_TWO;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_BIN;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_DESC;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_NAME;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_SURN;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_TYPE;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_URN;
import static net.yoedtos.blog.util.TestConstants.MEDIA_TWO_DESC;
import static net.yoedtos.blog.util.TestConstants.MEDIA_TWO_ID;
import static net.yoedtos.blog.util.TestConstants.MEDIA_TWO_NAME;
import static net.yoedtos.blog.util.TestConstants.MEDIA_TWO_SURN;
import static net.yoedtos.blog.util.TestConstants.MEDIA_TWO_TYPE;
import static net.yoedtos.blog.util.TestConstants.MEDIA_TWO_URN;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestUtil.createDate;
import static net.yoedtos.blog.util.TestUtil.createMedia;
import static net.yoedtos.blog.util.TestUtil.createMediaDTO;
import static net.yoedtos.blog.util.TestUtil.createMediaOne;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static net.yoedtos.blog.util.TestUtil.createUserTwo;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.FSException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.model.dto.MediaDTO;
import net.yoedtos.blog.model.entity.Media;
import net.yoedtos.blog.repository.dao.MediaDao;
import net.yoedtos.blog.repository.dao.UserDao;
import net.yoedtos.blog.repository.fs.MediaFS;

@RunWith(MockitoJUnitRunner.class)
public class MediaServiceTest {

	@Mock
	private UserDao userDao;
	@Mock
	private MediaFS mediaFSMock;
	@Mock
	private MediaDao mediaDaoMock;
	
	@InjectMocks
	private MediaService mediaService;
	
	@Captor
	private ArgumentCaptor<?> captor;
	
	private MediaDTO mediaDTO;
	private Media mediaOne;
	private Media media;
	private Date dateOne;
	
	@Before
	public void init() {
		dateOne = createDate(CREATE_ONE);
		
		mediaDTO = createMediaDTO(null);
		mediaDTO.setBinary(MEDIA_ONE_BIN);
		
		mediaOne = createMediaOne(dateOne);
		media = createMedia(new Date());
	}
	
	@Test
	public void whenCreateShouldCorrectCreate() throws ServiceException, FSException, DaoException {
		when(userDao.findByUsername(mediaDTO.getOwner())).thenReturn(createUserOne(dateOne));
		when(mediaFSMock.getUrn()).thenReturn(MEDIA_ONE_URN);
		
		mediaService.create(mediaDTO);
		
		verify(mediaFSMock).store((MediaDTO) captor.capture());
		MediaDTO mediaDtoCap = (MediaDTO) captor.getAllValues().get(0);
		
		assertThat(mediaDtoCap.getType(), equalTo(MEDIA_ONE_TYPE));
		assertThat(mediaDtoCap.getBinary(), equalTo(MEDIA_ONE_BIN));
		
		verify(mediaDaoMock).persist((Media) captor.capture());
		Media mediaCap = (Media) captor.getAllValues().get(1);
		assertThat(mediaCap, equalTo(media));
		
		verify(mediaFSMock, times(1)).store(mediaDTO);
		verify(mediaDaoMock, times(1)).persist(media);
	}
	
	@Test
	public void whenUpdateShouldReturnNull() throws ServiceException {
		MediaDTO nullMedia = mediaService.update(mediaDTO); 
		assertNull(nullMedia);
	}
	
	@Test(expected = ServiceException.class)
	public void whenCreateFailShouldThrowsFSException() throws ServiceException, FSException, DaoException {
		doThrow(new FSException()).when(mediaFSMock).store(mediaDTO);
		
		mediaService.create(mediaDTO);
		
		verify(mediaFSMock, times(1)).store(mediaDTO);
		verify(mediaDaoMock, times(0)).persist(mediaOne);
	}
	
	@Test(expected = ServiceException.class)
	public void whenCreateFailShouldThrowsDaoException() throws ServiceException, FSException, DaoException {
		when(userDao.findByUsername(mediaDTO.getOwner())).thenReturn(createUserOne(dateOne));
		when(mediaFSMock.getUrn()).thenReturn(MEDIA_ONE_URN);
		doThrow(new DaoException()).when(mediaDaoMock).persist(media);
		
		mediaService.create(mediaDTO);
		
		verify(mediaDaoMock, times(1)).persist(media);
		verify(mediaFSMock, times(1)).store(mediaDTO);
		verify(mediaFSMock, times(1)).drop(mediaDTO);
	}
	
	@Test
	public void whenGetShouldReturnMediaDTO() throws DaoException, ServiceException, FSException {
		when(mediaDaoMock.findById(MEDIA_ONE_ID)).thenReturn(mediaOne);
		when(mediaFSMock.find(MEDIA_ONE_URN)).thenReturn(MEDIA_ONE_BIN);
		
		MediaDTO mediaDTO = mediaService.get(MEDIA_ONE_ID);
		
		assertThat(mediaDTO.getId(), equalTo(MEDIA_ONE_ID));
		assertThat(mediaDTO.getName(), equalTo(MEDIA_ONE_NAME));
		assertThat(mediaDTO.getDescription(), equalTo(MEDIA_ONE_DESC));
		assertThat(mediaDTO.getContentType(), equalTo(CONTENT_TYPE_ONE));
		assertThat(mediaDTO.getBinary(), equalTo(MEDIA_ONE_BIN));
		
		verify(mediaDaoMock, times(1)).findById(MEDIA_ONE_ID);
		verify(mediaFSMock, times(1)).find(MEDIA_ONE_URN);
	}
	
	@Test(expected = ServiceException.class)
	public void whenRemoveFailShouldThrowsFSException() throws ServiceException, DaoException, FSException {
		MediaDTO mediaDto = new MediaDTO.Builder()
				.urn(MEDIA_ONE_URN)
				.build();
		
		when(mediaDaoMock.findById(MEDIA_ONE_ID)).thenReturn(mediaOne);
		doThrow(new FSException()).when(mediaFSMock).drop(mediaDto);
		
		mediaService.remove(MEDIA_ONE_ID);
		
		verify(mediaFSMock, times(1)).drop(mediaDto);
	}
	
	@Test
	public void whenRemoveShouldRemoveOnce() throws ServiceException, FSException, DaoException {
		when(mediaDaoMock.findById(MEDIA_ONE_ID)).thenReturn(mediaOne);
		
		mediaService.remove(MEDIA_ONE_ID);
		
		verify(mediaFSMock).drop((MediaDTO) captor.capture());
		MediaDTO mediaDto = (MediaDTO) captor.getAllValues().get(0);
		assertThat(mediaDto.getUrn(), equalTo(MEDIA_ONE_URN));
		
		verify(mediaDaoMock).remove((Long) captor.capture());
		Long mediaId = (Long) captor.getAllValues().get(1);
		assertThat(mediaId, equalTo(MEDIA_ONE_ID));

		verify(mediaDaoMock, times(1)).remove(MEDIA_ONE_ID);
		verify(mediaFSMock, times(1)).drop(mediaDto);
	}
	
	@Test
	public void whenGetAllShouldReturnTwoMedia() throws ServiceException, DaoException {	
		Date dateTwo = createDate(CREATE_TWO);
		Media mediaTwo = new Media.Builder()
				.id(MEDIA_TWO_ID)
				.createAt(dateTwo)
				.owner(createUserTwo(dateTwo))
				.name(MEDIA_TWO_NAME)
				.description(MEDIA_TWO_DESC)
				.contentType(CONTENT_TYPE_TWO)
				.urn(MEDIA_TWO_URN)
				.build();
		List<Media> mediaList = Arrays.asList(mediaOne, mediaTwo);
		
		when(mediaDaoMock.findAll()).thenReturn(mediaList);
		
		List<MediaDTO> mediaDTOs = mediaService.getAll();
		
		assertEquals(2, mediaDTOs.size());
		int index = 0;
		for (MediaDTO mediaDTO : mediaDTOs) {
			assertThat(mediaDTO.getId(), equalTo(mediaList.get(index).getId()));
			assertThat(mediaDTO.getCreateAt(), equalTo(mediaList.get(index).getCreateAt()));
			assertThat(mediaDTO.getDescription(), equalTo(mediaList.get(index).getDescription()));
			assertThat(mediaDTO.getName(), equalTo(mediaList.get(index).getName()));
			assertThat(mediaDTO.getOwner(), equalTo(mediaList.get(index).getOwner().getUsername()));
			assertThat(mediaDTO.getContentType(), equalTo(mediaList.get(index).getContentType()));
			index++;
		}
		
		assertThat(mediaDTOs.get(0).getType(), equalTo(MEDIA_ONE_TYPE));
		assertThat(mediaDTOs.get(1).getType(), equalTo(MEDIA_TWO_TYPE));
		assertThat(mediaDTOs.get(0).getUrn(), equalTo(MEDIA_ONE_SURN));
		assertThat(mediaDTOs.get(1).getUrn(), equalTo(MEDIA_TWO_SURN));
	}
	
	@Test
	public void whenGetAllByUserShouldReturnOneMedia() throws ServiceException, DaoException {	
		List<Media> mediaList = Arrays.asList(mediaOne);
		
		when(mediaDaoMock.findAllByUser(USERNAME_ONE)).thenReturn(mediaList);
		
		List<MediaDTO> mediaDTOs = mediaService.getAllByUser(USERNAME_ONE);
		
		assertEquals(1, mediaDTOs.size());
		assertThat(mediaDTOs.get(0).getId(), equalTo(MEDIA_ONE_ID));
		assertThat(mediaDTOs.get(0).getCreateAt(), equalTo(mediaOne.getCreateAt()));
		assertThat(mediaDTOs.get(0).getDescription(), equalTo(MEDIA_ONE_DESC));
		assertThat(mediaDTOs.get(0).getName(), equalTo(MEDIA_ONE_NAME));
		assertThat(mediaDTOs.get(0).getOwner(), equalTo(USERNAME_ONE));
		assertThat(mediaDTOs.get(0).getContentType(), equalTo(CONTENT_TYPE_ONE));
		assertThat(mediaDTOs.get(0).getType(), equalTo(MEDIA_ONE_TYPE));
		assertThat(mediaDTOs.get(0).getUrn(), equalTo(MEDIA_ONE_SURN));
	}
}
