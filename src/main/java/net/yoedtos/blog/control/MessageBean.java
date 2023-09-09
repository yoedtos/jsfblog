package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.HOME_REDIRECT;
import static net.yoedtos.blog.control.Constants.INDEX_REDIRECT;
import static net.yoedtos.blog.control.Constants.MESSAGE_ID;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.dto.MessageDTO;
import net.yoedtos.blog.service.MessageService;

@ManagedBean(name="message")
@ViewScoped
public class MessageBean extends AbstractBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageBean.class);
			
	private MessageDTO dto;
	private DataModel<MessageDTO> messages;
	private MessageService messageService;
	
	public MessageBean() {
		super();
		messageService = ServiceFactory.create(MessageService.class);
		dto = new MessageDTO.Builder().build();
	}
	
	public void loadMessages() {
		int roleValue = (int) getSessionAttribute(Constants.SESSION_KEY_ROLE);
		List<MessageDTO> messagesDto = new ArrayList<>();
		if(roleValue == Role.ADMINISTRATOR.getValue()) {
			try {
				messagesDto = messageService.getAll();
			} catch (ServiceException e) {
				LOGGER.error(e.getMessage());
				showErrorMessage(false);
			}
		}
		
		messages = new ListDataModel<>(messagesDto);
	}
	
	public String send() {
			dto.setHostAddress(getRemoteAddress());
			try {
				messageService.create(dto);
			} catch (ServiceException e) {
				LOGGER.error(e.getMessage());
				showErrorMessage(false);
				return "";
			}
		
			return INDEX_REDIRECT;
	}

	public String delete() {
		String messageId = getRequestParam(MESSAGE_ID);
		try {
			messageService.remove(Long.parseLong(messageId));
		} catch (NumberFormatException | ServiceException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
			return "";
		}
		return HOME_REDIRECT;
	}
	
	public MessageDTO getDto() {
		return dto;
	}
	
	public void setDto(MessageDTO dto) {
		this.dto = dto;
	}	
	
	public DataModel<MessageDTO> getMessages() {
		return messages;
	}
	
	public void setMessages(DataModel<MessageDTO> messages) {
		this.messages = messages;
	}
}
