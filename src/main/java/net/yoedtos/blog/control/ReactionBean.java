package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.COMMENT_PAR_ID;
import static net.yoedtos.blog.control.Constants.POST_PAR_ID;
import static net.yoedtos.blog.control.Constants.PARAM_VIEW_REDIRECT;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.dto.CommentDTO;
import net.yoedtos.blog.model.dto.ReplyDTO;
import net.yoedtos.blog.service.ReactionService;

@ManagedBean(name="reaction")
@RequestScoped
public class ReactionBean extends AbstractBean  {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReactionBean.class);
	
	private CommentDTO comment;
	private ReplyDTO reply;
	private ReactionService reactionService;
	
	@ManagedProperty(value="#{auth}")
	private AuthBean authBean;
	
	public ReactionBean() {
		super();
		this.comment = new CommentDTO.Builder(null).build();
		this.reply = new ReplyDTO.Builder(null).build();
		this.reactionService = ServiceFactory.create(ReactionService.class);
	}
	
	public String doComment() {
		try {
			comment.setPostId(Long.parseLong(getRequestParam(POST_PAR_ID)));
			comment.setHostAddress(getRemoteAddress());
			reactionService.create(comment);
		} catch (NumberFormatException | ServiceException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
		}
		return PARAM_VIEW_REDIRECT;
	}
	
	public String doReply() {
		try {
			reply.setCommentId(Long.parseLong(getRequestParam(COMMENT_PAR_ID)));
			reply.setAuthor(authBean.getUser().getUsername());
			reply.setHostAddress(getRemoteAddress());
			reactionService.create(reply);
		} catch (NumberFormatException | ServiceException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
		}
		return PARAM_VIEW_REDIRECT;
	}
	public CommentDTO getComment() {
		return comment;
	}
	
	public void setComment(CommentDTO comment) {
		this.comment = comment;
	}

	public ReplyDTO getReply() {
		return reply;
	}
	
	public void setReply(ReplyDTO reply) {
		this.reply = reply;
	}
	
	public void setAuthBean(AuthBean authBean) {
		this.authBean = authBean;
	}
}
