package controllers.servlets;

import controllers.ScheduledLineController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "deletescheduledline", urlPatterns = {"/deletescheduledline"})
public class DeleteScheduledLineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ScheduledLineController slc = new ScheduledLineController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getParameterMap().forEach((key, value) -> System.out.println(key + " " + Arrays.toString(value)));
        String[] ids = req.getParameterValues("ids[]");

        try {
            for (String id : ids)
                slc.deleteUserScheduledLine(Integer.parseInt(id));

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
