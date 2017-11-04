package model;

import static org.junit.Assert.assertTrue;

import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.entities.Line;
import model.entities.UserScheduledLine;
import model.entities.UserScheduledLine.Day;
import utils.DBInitialisation;

/**
 * @author Mohamed T. KASSAR
 *
 */

public class EntitiesTest {

	@Test
	public void userScheduledLineTest() {

		DBInitialisation.initLines();
		try {
			DBInitialisation.initUsers();
		} catch (DataException e) {
			e.printStackTrace();
			Assert.fail();
		}

		UserScheduledLine sc = new UserScheduledLine();

		sc.setLine(DAOFactory.lineDAO().getByNameNType("b", Line.LineType.rer));
		sc.setBeginTime(LocalTime.of(8, 10));
		sc.setEndTime(LocalTime.of(8, 30));
		sc.setDay(Day.friday);
		sc.setUser(DAOFactory.userDAO().getByName("user0"));

		DAOFactory.userScheduledLineDAO().save(sc);

		System.out.println(sc.getId());
		
		UserScheduledLine sc1 = DAOFactory.userScheduledLineDAO().getUserScheduledLineByAllInfos(
				DAOFactory.lineDAO().getByNameNType("b", Line.LineType.rer), DAOFactory.userDAO().getByName("user0"),
				LocalTime.of(8, 10), LocalTime.of(8, 30), Day.friday);

		assertTrue(sc1.getBeginTime().equals(sc.getBeginTime()));
		
		 UserScheduledLine[] result = DAOFactory.userScheduledLineDAO().getSchedulesByTime(8, 15, Day.friday).toArray(new UserScheduledLine[0]);
		 if(result.length == 0)
			 Assert.fail();
		 
		Assert.assertTrue(sc1.getId() == result[0].getId());
		
	}

}
