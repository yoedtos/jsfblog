package net.yoedtos.blog.repository.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Media;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class MediaDao extends AbstractDao<Media> implements Repository<Media>{
	private static final Logger LOGGER = LoggerFactory.getLogger(MediaDao.class);
			
	public MediaDao() {
		super(Media.class);
	}
	
	@Override
	public void persist(Media media) throws DaoException {
		try {
			save(media);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public void remove(Long id) throws DaoException {
		try {
			delete(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public Media merge(Media media) throws DaoException {
		return null;
	}

	@Override
	public Media findById(Long id) throws DaoException {
		Media mediaDb;
		try {
			mediaDb = loadById(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return mediaDb;
	}

	@Override
	public List<Media> findAll() throws DaoException {
		List<Media> medias;
		try {
			medias = loadByQuery("Media.loadAll");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return medias;
	}
}
