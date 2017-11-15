package controllers.servlets;

import controllers.ScheduledLineController;
import model.entities.UserScheduledLine;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet(name = "getscheduledlines", urlPatterns = {"/getscheduledlines"})
public class GetScheduledLinesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ScheduledLineController slc = new ScheduledLineController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<UserScheduledLine> scheduledLines = slc.getAllUserScheduledLine(req.getSession().getAttribute("username").toString());
        System.out.println(scheduledLines);
        resp.setHeader("Content-Type","application/json; charset=utf-8");
        ServletOutputStream out = resp.getOutputStream();
        out.println(scheduledLines.toString());
        out.flush();
        out.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
