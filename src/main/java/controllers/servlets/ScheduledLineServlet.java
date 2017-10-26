package controllers.servlets;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.ScheduledLineController;
import controllers.exceptions.DataException;

@WebServlet(name = "scheduledline", urlPatterns = {"/scheduledline"})
public class ScheduledLineServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ScheduledLineController slc = new ScheduledLineController();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String LineName = req.getParameter("lineName");
		String type = req.getParameter("type");
		String minute = req.getParameter("minute");
		String hour = req.getParameter("hour");
		String day = req.getParameter("day");
		req.getParameterMap().forEach((key, value) -> System.out.println(key + " " + Arrays.toString(value)));
		
		try {
			HttpSession session = req.getSession(true);
			if(session.isNew())
				throw new DataException("user logged out ");
			String login = (String) session.getAttribute("username");
			if(login.isEmpty())
				throw new DataException("cookie missing");
			ServletOutputStream out = resp.getOutputStream();
			slc.addScheduledLine(LineName, type,  Integer.parseInt(minute),  Integer.parseInt(hour), day, login);
			out.write("schedule added".toString().getBytes());
			out.flush();
			out.close();
		} catch (DataException e) {
			e.printStackTrace();
			resp.sendRedirect("");
		}
	}



}
