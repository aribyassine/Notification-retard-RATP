package controllers.servlets;

import controllers.AuthentificationController;
import controllers.exceptions.DataException;
import model.entities.User;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AuthentificationController auth = new AuthentificationController();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pwd = req.getParameter("pwd");

        try {
            auth.checkLogin(login,pwd);
            HttpSession session = req.getSession(true);
            session.setAttribute("username", login);
            resp.sendRedirect(req.getContextPath() + "/");

        } catch (DataException e) {
            e.printStackTrace();
            req.setAttribute("err",e.getMessage());
            this.getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(req,resp);
        }
    }
    
}
