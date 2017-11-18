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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "updatescheduledline", urlPatterns = {"/updatescheduledline"})
public class UpdateScheduledLineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ScheduledLineController slc = new ScheduledLineController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getParameterMap().forEach((k, v) -> System.out.println(k + " - " + Arrays.toString(v)));
        try {
            List<Integer> ids = Arrays.stream(req.getParameterValues("ids[]")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            String LineName = req.getParameter("lineName");
            String type = req.getParameter("type");
            String[] interval = req.getParameterValues("interval[]");
            String[] days = req.getParameterValues("days[]");

            HttpSession session = req.getSession();
            String login = (String) session.getAttribute("username");

            if (login.isEmpty()) throw new DataException("cookie missing");

            List<Integer> newIds = new ArrayList<>();
            for (Integer id : ids) {
                newIds = slc.modifyUserScheduledLine(id, LineName, type, interval[0], interval[1], days, login)
                        .stream().mapToInt(UserScheduledLine::getId).boxed().collect(Collectors.toList());
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setHeader("Content-Type", "application/json; charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            ServletOutputStream out = resp.getOutputStream();
            out.write(("{\"ids\":" + newIds + "}").getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}
