package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.AppException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.dto.PostDTO;
import net.yoedtos.blog.service.PostService;

@ManagedBean(name="post")
@ViewScoped
public class PostBean extends AbstractBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(PostBean.class);
	
	private PostDTO dto;
	private boolean editMode = false;
	private ListDataModel<PostDTO> posts;
	private PostService postService;
	
	@ManagedProperty(value="#{auth}")
	private AuthBean authBean;
	
	public PostBean() {
		super();
		postService = ServiceFactory.create(PostService.class);
	}

	@PostConstruct
	public void init() {
		dto = new PostDTO.Builder(authBean.getUser().getUsername()).build();
	}
	
	public void loadPost() {
		 if((dto.getId() != null) && (editMode == false)) {
			try {
				PostDTO postDto = postService.get(dto.getId());
				if(hasRoleRight() || hasOwnerRight(postDto.getAuthor())) {
					editMode = true;
					dto = postDto;
				} else {
					redirect(NOT_FOUND);
				}
			} catch (ServiceException e) {
				LOGGER.error(e.getMessage());
				showErrorMessage(false);
			} catch (IOException e) {
				throw new AppException(e.getMessage());
			}
		}
	}
	
	public void allPostByUser() {
		List<PostDTO> postDtos = new ArrayList<>();
		
		try {
			if(hasRoleRight()) {
				postDtos = postService.getAll();
			} else {
				postDtos = postService.getAllByUser(authBean.getUser().getUsername());
			}
		} catch (Exception e) {
			showErrorMessage(false);
		}
		posts = new ListDataModel<>(postDtos);
	}
	
	public String save() {
		try {
			postService.create(dto);
		} catch (ServiceException e) {
			showErrorMessage(false);
			return "";
		}
		showSuccessMessage(true);
		
		return POSTS_REDIRECT;
	}
	
	public String update() {
		editMode = false;
		
		try {
			postService.update(dto);
		} catch (ServiceException e) {
			showErrorMessage(false);
			return "";
		}
		showSuccessMessage(true);
		
		return POSTS_REDIRECT;
	}
	
	public String delete() {
		String pid = getRequestParam(POST_ID);
		try {
			postService.remove(Long.parseLong(pid));
		} catch (NumberFormatException | ServiceException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
			return "";
		}
		return POSTS_REDIRECT;
	}
	
	public void clear() {
		try {
			redirect(POST);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
		}
	}

	public void cancel() {
		editMode = false;
		try {
			redirect(POSTS);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
		}
	}
	
	public PostDTO getDto() {
		return dto;
	}
	
	public void setDto(PostDTO dto) {
		this.dto = dto;
	}
	
	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	
	public void setAuthBean(AuthBean authBean) {
		this.authBean = authBean;
	}
	
	public ListDataModel<PostDTO> getPosts() {
		return posts;
	}

	public void setPosts(ListDataModel<PostDTO> posts) {
		this.posts = posts;
	}
	
	private boolean hasOwnerRight(String author) {
		return authBean.getUser().getUsername().equals(author);
	}
	
	private boolean hasRoleRight() {
		Role userRole = authBean.getUser().getRole();
		return userRole == Role.MODERATOR || userRole == Role.ADMINISTRATOR;
	}
}	
