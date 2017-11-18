package controllers.servlets;

import controllers.ScheduledLineController;
import controllers.exceptions.DataException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "addscheduledline", urlPatterns = {"/addscheduledline"})
public class AddScheduledLineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ScheduledLineController slc = new ScheduledLineController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getParameterMap().forEach((k, v) -> System.out.println(k + " - " + Arrays.toString(v)));

        try {
            String LineName = req.getParameter("lineName");
            String type = req.getParameter("type");
            String[] interval = req.getParameterValues("interval[]");
            String[] days = req.getParameterValues("days[]");

            HttpSession session = req.getSession(true);
            if (session.isNew())
                throw new DataException("user logged out");
            String login = (String) session.getAttribute("username");
            if (login.isEmpty())
                throw new DataException("cookie missing");
            System.out.println(slc.addUserScheduledLine(LineName, type, interval[0], interval[1], days, login));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
