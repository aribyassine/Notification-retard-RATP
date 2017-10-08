package model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.dao.DAOFactory;
import model.dao.LineDAO;
import model.dao.ScheduleDAO;
import model.dao.ScheduledLineDAO;
import model.entities.Line;
import model.entities.Schedule;
import model.entities.ScheduledLine;

/**
 * @author Mohamed T. KASSAR
 *
 */

public class EntitiesTest {

	@Test
	public void scheduledLineTest() {
		initDB();
		Schedule sc = DAOFactory.scheduleDAO().getById(1);
		assertTrue(sc.getHour() == 8);
		assertTrue(sc.getScheduledLines().size() == 2);
		System.err.println( "hour : " + sc.getHour() + ", " + sc.getScheduledLines());

	}
	
	private void initDB() {
		LineDAO ld = DAOFactory.lineDAO();


		Line line11 = new Line();
		line11.setLineName("11express");
		line11.setLineType(Line.LineType.tramway);
		ld.save(line11);

		Line lineB = new Line();
		lineB.setLineName("b");
		lineB.setLineType(Line.LineType.rer);
		ld.save(lineB);

		Line lineD = new Line();
		lineD.setLineName("d");
		lineD.setLineType(Line.LineType.rer);
		ld.save(lineD);

		
		ScheduleDAO sd = DAOFactory.scheduleDAO();
		
		Schedule sat8am = new Schedule();
		sat8am.setDay(Schedule.Day.saturday);
		sat8am.setHour(8);
		sd.save(sat8am);

		Schedule sat9am = new Schedule();
		sat9am.setDay(Schedule.Day.saturday);
		sat9am.setHour(9);
		sd.save(sat9am);

		
		ScheduledLineDAO sld = DAOFactory.scheduledLineDAO();
		
		ScheduledLine ls = new ScheduledLine();
		ls.setLine(line11);
		ls.setSchedule(sat9am);
		sld.save(ls);

		ls = new ScheduledLine();
		ls.setLine(line11);
		ls.setSchedule(sat8am);
		sld.save(ls);

		ls = new ScheduledLine();
		ls.setLine(lineB);
		ls.setSchedule(sat9am);
		sld.save(ls);

		ls = new ScheduledLine();
		ls.setLine(lineB);
		ls.setSchedule(sat8am);
		sld.save(ls);

	}
}
