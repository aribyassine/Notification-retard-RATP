
package controllers.servlets;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.UserNotificationsController;
import controllers.exceptions.DataException;
import model.entities.Comment;
import model.entities.Notification;

@WebServlet(name = "usernotifications", urlPatterns = { "/usernotifications" })
public class UserNotifications extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserNotificationsController nct = new UserNotificationsController();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String login = (String) req.getSession().getAttribute("username");
			List<Notification> nots = nct.getUserNotifications(login).stream()
					.sorted((n1, n2) -> n2.getDate().compareTo(n1.getDate())).collect(Collectors.toList());
			req.setAttribute("notifications", nots);
			req.getRequestDispatcher("/jsp/userNotifications.jsp").forward(req, resp);
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}