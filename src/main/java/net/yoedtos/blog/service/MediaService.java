package net.yoedtos.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.FSException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.factory.StorageFactory;
import net.yoedtos.blog.model.dto.MediaDTO;
import net.yoedtos.blog.model.entity.Media;
import net.yoedtos.blog.repository.dao.MediaDao;
import net.yoedtos.blog.repository.dao.UserDao;
import net.yoedtos.blog.repository.fs.MediaFS;

public class MediaService extends AbstractService<MediaDTO> implements Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(MediaService.class);
	
	private MediaFS mediaFS;
	private MediaDao mediaDao;
	private UserDao userDao;
	
	public MediaService() {
		super();
		this.mediaFS = StorageFactory.create(MediaFS.class);
		this.mediaDao = RepositoryFactory.create(MediaDao.class);
		this.userDao = RepositoryFactory.create(UserDao.class);
	}
	
	@Override
	public void create(MediaDTO mediaDto) throws ServiceException {
		Media media = Media.convert(mediaDto);
		try {
			media.setOwner(userDao.findByUsername(mediaDto.getOwner()));
			mediaFS.store(mediaDto);
			media.setUrn(mediaFS.getUrn());
			mediaDao.persist(media);
		} catch (FSException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		} catch (DaoException e) {
			try {
				mediaFS.drop(new MediaDTO.Builder().urn(media.getUrn()).build());
			} catch (FSException ee) {
				LOGGER.error(ee.getMessage());
				throw new ServiceException(ee.getMessage());
			}
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}	
	}

	@Override
	public MediaDTO get(Long id) throws ServiceException {
		 MediaDTO mediaDTO;
		try {
			Media media = mediaDao.findById(id);
			String urn = media.getUrn();
			mediaDTO = MediaDTO.convert(media);
			mediaDTO.setBinary((byte[]) mediaFS.find(urn));
		} catch (DaoException | FSException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return mediaDTO;
	}
	
	@Override
	public void remove(Long id) throws ServiceException {
		try {
			Media media = mediaDao.findById(id);
			mediaFS.drop(new MediaDTO.Builder().urn(media.getUrn()).build());
			mediaDao.remove(id);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		} catch (FSException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public MediaDTO update(MediaDTO t) throws ServiceException {
		return null;
	}

	@Override
	public List<MediaDTO> getAll() throws ServiceException {
		List<Media> medias;
		try {
			medias = mediaDao.findAll();
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return medias.stream()
				.map(MediaDTO::convert)
				.collect(Collectors.toList());
	}
}
