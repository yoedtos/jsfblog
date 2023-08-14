package net.yoedtos.blog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.dto.CommentDTO;
import net.yoedtos.blog.model.dto.ReplyDTO;

public class ReactionService implements Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReactionService.class);
	
	private CommentService commentService;
	private ReplyService replyService;
	
	public ReactionService() {
		commentService = ServiceFactory.create(CommentService.class);
		replyService = ServiceFactory.create(ReplyService.class);
	}
	
	public void create(CommentDTO commentDTO) throws ServiceException {
		commentService.create(commentDTO);
	}
	
	public void create(ReplyDTO replyDTO) throws ServiceException {
		replyService.create(replyDTO);
	}
	
	public List<Entry<CommentDTO, List<ReplyDTO>>> get(Long id) throws ServiceException {
		Map<CommentDTO, List<ReplyDTO>> reactions = new HashMap<>();
		try {
			List<CommentDTO> commentDTOs = commentService.getAllByPost(id);
			for (CommentDTO commentDTO : commentDTOs) {
				Long commentId = commentDTO.getId();
				reactions.put(commentDTO, replyService.getAllByComment(commentId));
			}
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		return new ArrayList<>(reactions.entrySet());
	}
}
