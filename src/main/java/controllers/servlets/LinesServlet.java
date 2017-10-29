package controllers.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.LinesController;

/**
 * @author Ayyoub LABIB
 */

@WebServlet(name = "lines", urlPatterns = {"/lines"})
public class LinesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type","application/json");
		resp.setHeader("Access-Control-Allow-Origin","*");
		ServletOutputStream out = resp.getOutputStream();
		try {
			out.write(new LinesController().getLinesOnJSON().getBytes());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("");
		}



	}



}
