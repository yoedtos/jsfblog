package net.yoedtos.blog.repository.fs;

import static net.yoedtos.blog.util.TestConstants.CREATE_ONE;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_BIN;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_NAME;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_URN;
import static net.yoedtos.blog.util.TestUtil.createDate;
import static net.yoedtos.blog.util.TestUtil.createUrnOne;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.FSException;
import net.yoedtos.blog.factory.StorageFactory;
import net.yoedtos.blog.model.dto.MediaDTO;
import net.yoedtos.blog.util.Constants;
import net.yoedtos.blog.util.TestUtil;

@RunWith(MockitoJUnitRunner.class)
public class MediaFSTest {

	@Mock
	private FSHelper fsHelperMock = new FSHelper(Constants.MEDIA_PATH);
	
	@InjectMocks
	private MediaFS mediaFS = StorageFactory.create(MediaFS.class);
	
	private MediaDTO mediaDTO;
	private MediaDTO mediaDtoDrop;
	private String todayUrnOne;
	
	@Before
	public void init() {
		todayUrnOne = createUrnOne(MEDIA_ONE_NAME);
		mediaDTO = TestUtil.createMediaDTO(createDate(CREATE_ONE));
		mediaDTO.setBinary(MEDIA_ONE_BIN);
		mediaDtoDrop = new MediaDTO.Builder().urn(MEDIA_ONE_URN).build();
	}
	
	@Test(expected = FSException.class)
	public void whenStoreFailShouldThrowException() throws FSException, IOException {
		doThrow(new IOException()).when(fsHelperMock).write(todayUrnOne, MEDIA_ONE_BIN);
		mediaFS.store(mediaDTO);
		verify(fsHelperMock, times(1)).write(todayUrnOne, MEDIA_ONE_BIN);
	}
	 
	@Test
	public void whenStoreShouldCallWriteCorrectly() throws FSException, IOException {
		mediaFS.store(mediaDTO);
		verify(fsHelperMock, times(1)).write(todayUrnOne, MEDIA_ONE_BIN);
	}
	
	@Test
	public void whenFindShouldReturnMediaBinary() throws FSException, IOException {
		when(fsHelperMock.read(MEDIA_ONE_URN)).thenReturn(MEDIA_ONE_BIN);
		byte[] mediaBinary = (byte[]) mediaFS.find(MEDIA_ONE_URN);
		assertThat(mediaBinary, equalTo(MEDIA_ONE_BIN));
	}
	
	@Test(expected = FSException.class)
	public void whenDropFailShouldThrowException() throws FSException, IOException {
		doThrow(new IOException()).when(fsHelperMock).delete(MEDIA_ONE_URN);
		mediaFS.drop(mediaDtoDrop);
		verify(fsHelperMock, times(1)).delete(MEDIA_ONE_URN);
	}
	
	@Test
	public void whenDropShouldRemoveOnce() throws FSException, IOException {
		mediaFS.drop(mediaDtoDrop);
		verify(fsHelperMock, times(1)).delete(MEDIA_ONE_URN);
	}
	
	@Test
	public void whenLoadShouldReturnNull() throws FSException {
		MediaDTO nullMediaDto = mediaFS.load(null);
		assertNull(nullMediaDto);
	}
	
	@Test
	public void whenReplaceShouldReturnNull() throws FSException {
		MediaDTO nullMediaDto = mediaFS.replace(mediaDTO);
		assertNull(nullMediaDto);
	}
}
