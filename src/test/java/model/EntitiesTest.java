package model;

import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import model.entities.Line;
import model.entities.Schedule;
import model.entities.ScheduledLine;
import util.HibernateUtil;

/**
 * @author Mohamed T. KASSAR
 *
 */

public class EntitiesTest {

	@Test
	public void scheduledLineTest() {
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
		
		assertTrue(sc.getHour() == 8);
		assertTrue(sc.getScheduledLines().size() == 2);
		System.out.println( "hour : " + sc.getHour() + ", " + sc.getScheduledLines());
	}
}
