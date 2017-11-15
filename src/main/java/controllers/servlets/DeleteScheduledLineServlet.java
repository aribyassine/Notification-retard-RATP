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

@WebServlet(name = "deletescheduledline", urlPatterns = {"/deletescheduledline"})
public class DeleteScheduledLineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ScheduledLineController slc = new ScheduledLineController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
     

        req.getParameterMap().forEach((key, value) -> System.out.println(key + " " + Arrays.toString(value)));

        try {
            HttpSession session = req.getSession(true);
            if (session.isNew())
                throw new DataException("user logged out");
            String login = (String) session.getAttribute("username");
            if (login.isEmpty())
                throw new DataException("cookie missing");
            
            slc.deleteUserScheduledLine(id) ;
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
