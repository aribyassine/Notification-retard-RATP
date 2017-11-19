

package controllers.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.UserNotificationsController;
import controllers.exceptions.DataException;


@WebServlet(name = "usernotifications", urlPatterns = {"/usernotifications"})
public class UserNotifications extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserNotificationsController nct = new UserNotificationsController();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
	        resp.setHeader("Content-Type","application/json; charset=UTF-8");
	        resp.setCharacterEncoding("UTF-8");
	        HttpSession session = req.getSession(true);
			if (session.isNew())
				throw new DataException("user logged out");
			String login = (String) session.getAttribute("username");
	        ServletOutputStream out = resp.getOutputStream();
	        out.write(nct.getUserNotifications(login).toString().getBytes());
	        out.flush();
	        out.close();
			resp.setStatus(HttpServletResponse.SC_OK);
		}catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



	}

}