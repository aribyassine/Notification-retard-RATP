package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.entities.Line;
import util.HibernateUtil;

@WebServlet(name = "MyServlet", urlPatterns = { "/hello" })
public class HelloServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		
		s.beginTransaction();
		Line line = new Line();
		line.setLineName("11express");
		line.setLineType(Line.LineType.tramway);

		s.save(line);
		
		line = new Line();
		line.setLineName("b");
		line.setLineType(Line.LineType.rer);

		s.save(line);
		s.getTransaction().commit();

		s.close();

		s = factory.openSession();
		 s.beginTransaction();
		line = (Line) s.get(Line.class, 1);
		 s.getTransaction().commit();

		s.close();
		// s;

		ServletOutputStream out = resp.getOutputStream();
		out.write(("hello heroku" + "\n :" + line.getLineName()).getBytes());
		out.flush();
		out.close();
	}

}
