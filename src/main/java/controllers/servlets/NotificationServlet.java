package controllers.servlets;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.NotificationsController;
import controllers.ScheduledLineController;
import controllers.exceptions.DataException;
import model.entities.Comment;
import model.entities.Notification;
import model.entities.UserScheduledLine;


@WebServlet(name = "notification", urlPatterns = {"/notification"})
public class NotificationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private NotificationsController nct = new NotificationsController();
	private ScheduledLineController slc = new ScheduledLineController();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
		
			int id = Integer.parseInt(req.getParameter("id"));
			System.out.println("here");
			Notification not = nct.getNotificationById(id);
			ArrayList<Comment> com= new ArrayList<>(not.getComments());
			req.setAttribute("notification", not);
			req.setAttribute("comments", com);
			
			//System.out.println(not.toString());

			
			req.getRequestDispatcher("./Notification.jsp").forward(req,resp);
			resp.setStatus(HttpServletResponse.SC_OK);
		}catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.getParameterMap().forEach((k, v) -> System.out.println(k + " - " + Arrays.toString(v)));


	}

}