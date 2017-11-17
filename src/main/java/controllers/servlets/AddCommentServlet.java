package controllers.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.CommentController;
import controllers.ScheduledLineController;
import controllers.exceptions.DataException;
@WebServlet(name = "comment", urlPatterns = {"/comment"})
public class AddCommentServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	private CommentController slc = new CommentController();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// req.getParameterMap().forEach((k, v) -> System.out.println(k + " - " + Arrays.toString(v)));

		try {
			HttpSession session = req.getSession(true);
			if (session.isNew())
				throw new DataException("user logged out");
			String login = (String) session.getAttribute("username");
			System.out.println(login);
			System.out.println(req.getParameter("comment"));
			System.out.println(req.getParameter("notification"));
			slc.addComment(req.getParameter("comment"), "addScheduleLine_PositiveTest", Integer.parseInt(req.getParameter("notification")));
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (DataException e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
