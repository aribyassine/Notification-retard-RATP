package controllers.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.CommentController;
import controllers.NotificationsController;
import controllers.ScheduledLineController;
import controllers.UserNotificationsController;
import controllers.exceptions.DataException;
import model.entities.Comment;
import model.entities.Notification;


@WebServlet(name = "comments", urlPatterns = {"/comments"})
public class commentsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private NotificationsController notificationsController = new NotificationsController();
    private CommentController commentController = new CommentController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("notificationId"));
            Notification notification = notificationsController.getNotificationById(id);
            req.setAttribute("notification", notification);
            req.getRequestDispatcher("/jsp/comments.jsp").forward(req, resp);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getParameterMap().forEach((k, v) -> System.out.println(k + " - " + Arrays.toString(v)));
            int id = Integer.parseInt(req.getParameter("notificationId"));
            String com = req.getParameter("com");
            String login = (String) req.getSession().getAttribute("username");
            commentController.addComment(com, login, id);
            resp.sendRedirect("/comments?notificationId=" + id);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}