package net.yoedtos.blog.control.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.model.UploadedFile;
import net.yoedtos.blog.model.Type;
import net.yoedtos.blog.view.AlertMsg;

@FacesValidator("mediaValidator")
public class TypeValidator implements Validator {
		
	private static final int FILE_SIZE_MAX = 1024 * 1024 * 1; //1MB
	
	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		
		Type type = Type.valueOf((String)comp.getAttributes().get("types"));
		UploadedFile mediaFile = (UploadedFile) value;
		
		if(hasData(mediaFile)) {
			throw new ValidatorException(AlertMsg.getInstance()
										.setMessage(FacesMessage.SEVERITY_ERROR, "msg.file_invld"));
		}
		
		boolean valid = false;
		String contentType = mediaFile.getContentType();
		
		switch (type) {
		case DOC:
			for(MimeType.Document doc : MimeType.Document.values()) {
				if(doc.getContentType().equals(contentType)) {
					valid = true;
					break;
				}
			}
			break;
		case IMG:
			for(MimeType.Image image : MimeType.Image.values()) {
				if(image.getContentType().equals(contentType)) {
					valid = true;
					break;
				}
			}		
			break;
		case VID:
			for(MimeType.Video video : MimeType.Video.values()) {
				if(video.getContentType().equals(contentType)) {
					valid = true;
					break;
				}
			}		
			break;
		case ZIP:
			for(MimeType.Compress compress : MimeType.Compress.values()) {
				if(compress.getContentType().equals(contentType)) {
					valid = true;
					break;
				}
			}		
			break;			
		default:
			break;
		}
		
		if(!valid) {
			throw new ValidatorException(AlertMsg.getInstance()
										.setMessage(FacesMessage.SEVERITY_ERROR, "msg.file_fmt_ng"));
		}

		if (mediaFile.getSize() > FILE_SIZE_MAX ) {
			throw new ValidatorException(AlertMsg.getInstance()
										.setMessage(FacesMessage.SEVERITY_ERROR, "msg.file_size_ng"));
		}
	}

	private boolean hasData(UploadedFile mediaFile) {
		return mediaFile == null || mediaFile.getSize() <= 0 || mediaFile.getContentType().isEmpty();
	}
	
}
