package controllers.servlets;

import controllers.ScheduledLineController;
import controllers.exceptions.DataException;
import model.entities.UserScheduledLine;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "getscheduledlines", urlPatterns = {"/getscheduledlines"})
public class GetScheduledLinesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ScheduledLineController slc = new ScheduledLineController();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession(true);
			if (session.isNew())
				throw new DataException("user logged out");
			
			String login = (String) session.getAttribute("username");
			Set<UserScheduledLine> scheduledLines = slc.getAllUserScheduledLine(req.getSession().getAttribute("username").toString());
			System.out.println(scheduledLines);
			resp.setHeader("Content-Type","application/json; charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");

			ServletOutputStream out = resp.getOutputStream();
			out.write(scheduledLines.toString().getBytes());
			out.flush();
			out.close();
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (DataException e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
