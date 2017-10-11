package model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.dao.DAOFactory;
import model.entities.Schedule;
import utils.DBInitialisation;

/**
 * @author Mohamed T. KASSAR
 *
 */

public class EntitiesTest {

	@Test
	public void scheduledLineTest() {
		DBInitialisation.initScheduledLines();
		Schedule sc = DAOFactory.scheduleDAO().getById(1);
		assertTrue(sc.getHour() == 8);
		assertTrue(sc.getScheduledLines().size() == 2);
		System.err.println("hour : " + sc.getHour() + ", " + sc.getScheduledLines());
	}

}
