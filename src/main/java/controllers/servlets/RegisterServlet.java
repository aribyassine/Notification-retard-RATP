package controllers.servlets;

import controllers.AuthentificationController;
import controllers.exceptions.DataException;
import model.entities.User;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "register", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AuthentificationController auth = new AuthentificationController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/register.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String pwd = req.getParameter("pwd");
        req.getParameterMap().forEach((key, value) -> System.out.println(key + " " + Arrays.toString(value)));

        try {
            User user = auth.registerUser(login,email,phone,pwd);
            ServletOutputStream out = resp.getOutputStream();
            out.write(user.toString().getBytes());
            out.flush();
            out.close();
        } catch (DataException e) {
            e.printStackTrace();
            resp.sendRedirect("");
        }
    }

}
