package model;

import static org.junit.Assert.assertTrue;

import java.time.LocalTime;

import model.entities.ClientScheduledLine;
import org.junit.Assert;
import org.junit.Test;

import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.entities.Line;
import model.entities.ClientScheduledLine.Day;
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

		ClientScheduledLine sc = new ClientScheduledLine();

		sc.setLine(DAOFactory.lineDAO().getByNameNType("b", Line.LineType.rer));
		sc.setBeginTime(LocalTime.of(8, 10));
		sc.setEndTime(LocalTime.of(8, 30));
		sc.setDay(Day.friday);
		sc.setClient(DAOFactory.userDAO().getByName("user0"));

		DAOFactory.userScheduledLineDAO().save(sc);

		System.out.println(sc.getId());
		
		ClientScheduledLine sc1 = DAOFactory.userScheduledLineDAO().getUserScheduledLineByAllInfos(
				DAOFactory.lineDAO().getByNameNType("b", Line.LineType.rer), DAOFactory.userDAO().getByName("user0"),
				LocalTime.of(8, 10), LocalTime.of(8, 30), Day.friday);

		assertTrue(sc1.getBeginTime().equals(sc.getBeginTime()));
		
		 ClientScheduledLine[] result = DAOFactory.userScheduledLineDAO().getSchedulesByTime(8, 15, Day.friday).toArray(new ClientScheduledLine[0]);
		 if(result.length == 0)
			 Assert.fail();
		 
		Assert.assertTrue(sc1.getId() == result[0].getId());
		
	}

}
