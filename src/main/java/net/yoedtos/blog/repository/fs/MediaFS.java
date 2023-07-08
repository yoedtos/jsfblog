package net.yoedtos.blog.repository.fs;

import static net.yoedtos.blog.util.Constants.DOCS;
import static net.yoedtos.blog.util.Constants.FILES;
import static net.yoedtos.blog.util.Constants.IMAGES;
import static net.yoedtos.blog.util.Constants.MEDIA_PATH;
import static net.yoedtos.blog.util.Constants.SLASH;
import static net.yoedtos.blog.util.Constants.VIDEOS;

import java.io.IOException;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.FSException;
import net.yoedtos.blog.model.Type;
import net.yoedtos.blog.model.dto.MediaDTO;
import net.yoedtos.blog.repository.Storage;

public class MediaFS implements Storage<MediaDTO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MediaFS.class);

	private String urn;
	private FSHelper fsHelper;

	public MediaFS() {
		this.fsHelper = new FSHelper(MEDIA_PATH);
	}

	public String getUrn() {
		return this.urn;
	}
	
	@Override
	public void store(MediaDTO mediaDto) throws FSException {
		Type type = mediaDto.getType();

		try {
			String path;
			if (type == Type.VID) {
				path = createPath(VIDEOS);
			} else if (type == Type.IMG) {
				path = createPath(IMAGES);
			} else if (type == Type.DOC) {
				path = createPath(DOCS);
			} else {
				path = createPath(FILES);
			}

			fsHelper.createPath(path);

			checkMediaName(mediaDto, path);

			fsHelper.write(urn, mediaDto.getBinary());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new FSException(e.getMessage());
		}
	}

	@Override
	public void drop(MediaDTO mediaDto) throws FSException {
		String dropUrn = mediaDto.getUrn();

		try {
			fsHelper.delete(dropUrn);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new FSException(e.getMessage());
		}
	}

	@Override
	public Object find(Object urn) throws FSException {
		byte[] binary;
		try {
			binary = fsHelper.read((String) urn);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new FSException(e.getMessage());
		}
		return binary;
	}

	private String createPath(String type) {
		Calendar today = Calendar.getInstance();

		StringBuilder builder = new StringBuilder();
		builder.append(SLASH);
		builder.append(type);
		builder.append(SLASH);
		builder.append(Integer.toString(today.get(Calendar.YEAR)));
		builder.append(SLASH);
		builder.append(Integer.toString(today.get(Calendar.MONTH) + 1));
		builder.append(SLASH);
		
		return builder.toString();
	}

	private void checkMediaName(MediaDTO mediaDto, String path) {
		String name = mediaDto.getName();
		int counter = -1;
		do {
			if (counter > 0) {
				mediaDto.setName(counter + "_" + name);
			}
			urn = path + mediaDto.getName();
			counter++;
		} while (fsHelper.checkFile(urn));
	}
	
	@Override
	public MediaDTO load(Object obj) throws FSException {
		return null;
	}

	@Override
	public MediaDTO replace(MediaDTO t) throws FSException {
		return null;
	}
}
