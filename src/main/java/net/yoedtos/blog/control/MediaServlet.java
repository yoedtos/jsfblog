package net.yoedtos.blog.control;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.Type;
import net.yoedtos.blog.model.dto.MediaDTO;
import net.yoedtos.blog.service.MediaService;

@SuppressWarnings("serial")
@WebServlet("/media/*")
public class MediaServlet extends HttpServlet {
	private static final Logger LOGGER = LoggerFactory.getLogger(MediaServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String[] token = request.getPathInfo().substring(1).split("-");
		MediaService mediaService = ServiceFactory.create(MediaService.class);
		try {
			MediaDTO mediaDto = mediaService.get(Long.parseLong(token[0]));
			response.setContentType(mediaDto.getContentType());
			response.setContentLength(mediaDto.getBinary().length);
			
			Type type = mediaDto.getType();
			if (type.equals(Type.ZIP) || type.equals(Type.DOC)) {
				response.setHeader("Content-Disposition", createContentDisposition(mediaDto.getName()));
			}
			
			try (OutputStream responseOutputStream = response.getOutputStream()) {
				responseOutputStream.write(mediaDto.getBinary());
				responseOutputStream.flush();
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}			
		} catch (NumberFormatException | ServiceException e) {
			LOGGER.error(e.getMessage());
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private String createContentDisposition(String name) {
		return new StringBuilder()
				.append("attachment;filename=")
				.append("\"")
				.append(name)
				.append("\"")
				.toString();
	}
}
