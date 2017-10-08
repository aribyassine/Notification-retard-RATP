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
import model.entities.ScheduledLine;
import model.entities.Schedule;
import util.HibernateUtil;

@WebServlet(name = "MyServlet", urlPatterns = { "/hello" })
public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();

		s.beginTransaction();

		Line line11 = new Line();
		line11.setLineName("11express");
		line11.setLineType(Line.LineType.tramway);
		s.save(line11);

		Line lineB = new Line();
		lineB.setLineName("b");
		lineB.setLineType(Line.LineType.rer);
		s.save(lineB);

		Line lineD = new Line();
		lineB.setLineName("d");
		lineB.setLineType(Line.LineType.rer);
		s.save(lineD);

		Schedule sat8am = new Schedule();
		sat8am.setDay(Schedule.Day.saturday);
		sat8am.setHour(8);
		s.save(sat8am);

		Schedule sat9am = new Schedule();
		sat9am.setDay(Schedule.Day.saturday);
		sat9am.setHour(9);
		s.save(sat9am);

		ScheduledLine ls = new ScheduledLine();
		ls.setLine(line11);
		ls.setSchedule(sat9am);
		s.save(ls);

		ls = new ScheduledLine();
		ls.setLine(line11);
		ls.setSchedule(sat8am);
		s.save(ls);

		ls = new ScheduledLine();
		ls.setLine(lineB);
		ls.setSchedule(sat9am);
		s.save(ls);

		ls = new ScheduledLine();
		ls.setLine(lineB);
		ls.setSchedule(sat8am);
		s.save(ls);

		s.getTransaction().commit();

		s.close();

		s = factory.openSession();
		s.beginTransaction();
		Schedule sc = (Schedule) s.get(Schedule.class, 1);
		// s.getTransaction().commit();

		s.close();
		// s;

		ServletOutputStream out = resp.getOutputStream();
		out.write(("hello heroku" + "\n : hour : " + sc.getHour() + ", " + sc.getScheduledLines()).getBytes());
		out.flush();
		out.close();
	}

}
