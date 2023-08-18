package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.FILE_ID;
import static net.yoedtos.blog.control.Constants.MEDIA;
import static net.yoedtos.blog.control.Constants.MEDIAS_REDIRECT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.Type;
import net.yoedtos.blog.model.dto.MediaDTO;
import net.yoedtos.blog.service.MediaService;

@ManagedBean(name="media")
@ViewScoped
public class MediaBean extends AbstractBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(MediaBean.class);
	
	private String context;
	private MediaDTO dto;
	private String allowedTypes;
	private UploadedFile uploadedFile;
	private ListDataModel<MediaDTO> files;
	
	private MediaService mediaService;
	
	@ManagedProperty(value="#{auth}")
	private AuthBean authBean;
	
	public MediaBean() {
		super();
		mediaService = ServiceFactory.create(MediaService.class);
		dto = new MediaDTO.Builder().build();
		this.context = getURLWithContextPath() + MEDIA;
	}

	public void allFilesByUser() {
		List<MediaDTO> dtos = new ArrayList<>();
		try {
			Role roleValue = authBean.getUser().getRole();
			if(roleValue == Role.MODERATOR || roleValue == Role.ADMINISTRATOR) {
				dtos = mediaService.getAll();
			} else {
				dtos = mediaService.getAllByUser(authBean.getUser().getUsername());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
		}
		
		files = new ListDataModel<>(dtos);
	}
	
	public void changeType(ValueChangeEvent e) {
		Type type = (Type) e.getNewValue();
		dto.setType(type);
		
		if (type.equals(Type.DOC)) {
			allowedTypes = Type.DOC.name();
		} else if (type.equals(Type.ZIP)) {
			allowedTypes = Type.ZIP.name();
		} else if (type.equals(Type.VID)) {
			allowedTypes = Type.VID.name();
		} else if (type.equals(Type.IMG)) {
			allowedTypes = Type.IMG.name();
		}
	}
	
	public String upload() {
		String fileName = uploadedFile.getFileName().toLowerCase();
		dto.setContentType(uploadedFile.getContentType());
		dto.setName(fileName.replace(" ", "_"));
		dto.setOwner(authBean.getUser().getUsername());
		try {
			dto.setBinary(IOUtils.toByteArray(uploadedFile.getInputstream()));
			mediaService.create(dto);
		} catch (ServiceException | IOException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
			return "";
		}
		showSuccessMessage(true);
		return MEDIAS_REDIRECT;
	}

	public String delete() {
		try {
			mediaService.remove(Long.parseLong(getRequestParam(FILE_ID)));
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
			return "";
		}

		showSuccessMessage(true);
		return MEDIAS_REDIRECT;
	}

	public String getContext() {
		return context;
	}

	public Type[] getTypes() {
	        return Type.values();
	}
	
	public MediaDTO getDto() {
		return dto;
	}
	
	public void setDto(MediaDTO dto) {
		this.dto = dto;
	}
	
	public String getAllowedTypes() {
		return allowedTypes;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}
	
	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public ListDataModel<MediaDTO> getFiles() {
		return files;
	}
	
	public void setAuthBean(AuthBean authBean) {
		this.authBean = authBean;
	}
}
