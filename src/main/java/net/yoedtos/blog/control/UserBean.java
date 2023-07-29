
package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.USERS;
import static net.yoedtos.blog.control.Constants.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.service.UserService;

@ManagedBean(name = "user")
@ViewScoped
public class UserBean extends AbstractBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserBean.class);

	private ListDataModel<UserDTO> users;
	private UserService userService;

	public UserBean() {
		super();
		userService = ServiceFactory.create(UserService.class);
	}

	public void loadUsers() {
		List<UserDTO> userList = new ArrayList<>();
		try {
			userList = userService.getAll();
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
		}
		users = new ListDataModel<>(userList);
	}

	public void changeRole(ValueChangeEvent ev) {
		UserDTO user = users.getRowData();
		user.setRole(Role.valueOf(ev.getNewValue().toString()));
		try {
			userService.update(user);
			redirect(USERS);
		} catch (ServiceException | IOException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
		}
	}

	public void changeStatus(ValueChangeEvent ev) {
		UserDTO user = users.getRowData();
		boolean status = (Boolean) ev.getNewValue();
		user.setActive(status);
		try {
			userService.update(user);
			redirect(USERS);
		} catch (ServiceException | IOException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
		}
	}

	public String delete() {
		String userId = getRequestParam(USER_ID);
		try {
			userService.remove(Long.parseLong(userId));
		} catch (NumberFormatException | ServiceException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
			return "";
		}
		return USERS_REDIRECT;
	}

	public Role[] getRoleValues() {
		return Role.values();
	}

	public ListDataModel<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(ListDataModel<UserDTO> users) {
		this.users = users;
	}
}
