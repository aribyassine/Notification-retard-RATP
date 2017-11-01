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

	private static final long serialVersionUID = 1L;
	private ScheduledLineController slc = new ScheduledLineController();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String LineName = req.getParameter("lineName");
		String type = req.getParameter("type");
		String minute = req.getParameter("minuteBegin");
		String hour = req.getParameter("hourEnd");
		String[] days = new String[7];
		days[0]= req.getParameter("lundi");
		days[1] = req.getParameter("mardi");
		days[2] = req.getParameter("mercredi");
		days[3] = req.getParameter("jeudi");
		days[4]= req.getParameter("vendredi");
		days[5] = req.getParameter("samedi");
		days[6] = req.getParameter("dimanche");
	
		
		req.getParameterMap().forEach((key, value) -> System.out.println(key + " " + Arrays.toString(value)));
		
		try {
			HttpSession session = req.getSession(true);
			if(session.isNew())
				throw new DataException("user logged out ");
			String login = (String) session.getAttribute("username");
			if(login.isEmpty())
				throw new DataException("cookie missing");
			ServletOutputStream out = resp.getOutputStream();
			slc.addUserScheduledLine(LineName, type,  minute,  hour, days, login);
			out.write("schedule added".toString().getBytes());
			out.flush();
			out.close();
		} catch (DataException e) {
			e.printStackTrace();
			resp.sendRedirect("");
		}
	}



}
