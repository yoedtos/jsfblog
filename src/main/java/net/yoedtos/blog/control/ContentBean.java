package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.NOT_FOUND;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.AppException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.dto.CommentDTO;
import net.yoedtos.blog.model.dto.PostDTO;
import net.yoedtos.blog.model.dto.ReplyDTO;
import net.yoedtos.blog.service.PostService;
import net.yoedtos.blog.service.ReactionService;

@ManagedBean(name="content")
@ViewScoped
public class ContentBean extends AbstractBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContentBean.class);
	
	private String postId;
	private PostDTO dto;
	private List<Entry<CommentDTO, List<ReplyDTO>>> reactions;
	private PostService postService;
	private ReactionService reactionService;
		
	public ContentBean() {
		super();
		postService = ServiceFactory.create(PostService.class);
		reactionService = ServiceFactory.create(ReactionService.class);
		dto = new PostDTO.Builder(null).build();
	}
	
	public void loadPost() {
		 if(postId != null) {
			try {
				dto = postService.get(Long.parseLong(postId));
				reactions = reactionService.get(Long.parseLong(postId));
			} catch (NumberFormatException | ServiceException e) {
				LOGGER.error(e.getMessage());
				try {
					redirect(NOT_FOUND);
				} catch (IOException ex) {
					throw new AppException(ex.getMessage());
				}
			}
		}
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	public PostDTO getDto() {
		return dto;
	}
	
	public void setDto(PostDTO dto) {
		this.dto = dto;
	}
	
	public List<Entry<CommentDTO, List<ReplyDTO>>> getReactions() {
		return reactions;
	}
}	
