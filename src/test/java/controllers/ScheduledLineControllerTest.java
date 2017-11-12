package controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import model.dao.DAOFactory;
import model.entities.UserScheduledLine;
import model.entities.UserScheduledLine.Day;

/**
 * @author Mohamed T. KASSAR
 *
 */

public class ScheduledLineControllerTest {

	@Test
	public void addScheduleLine_PositiveTest() {
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0] = "f";
		days[1] = "f";
		days[2] = "f";
		days[5] = "f";
		days[6] = "true";
		days[3] = "true";
		days[4] = "true";
		AuthentificationController controller1 = new AuthentificationController();
		try {
			controller1.registerUser("addScheduleLine_PositiveTest", "tikoootikooo@gmail.com", "0769622218", "123lkj");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}

		try {
			assertNotNull(controller.addUserScheduledLine("b", "rer", "10:05", "10:11", days, "addScheduleLine_PositiveTest"));
		} catch (Exception e) {
			Assert.fail("add schedule line error : " + e.getMessage());
		}

		try {
			assertNotNull(controller.addUserScheduledLine("b", "rer", "10:02", "10:15", days, "addScheduleLine_PositiveTest"));
		} catch (Exception e) {
			e.printStackTrace(System.out);
			Assert.fail("add schedule line error : " + e.getMessage());
		}

		try {
			assertNotNull(controller.deleteUserScheduledLine(1));
		} catch (Exception e) {
			e.printStackTrace(System.out);
			Assert.fail("add schedule line error : " + e.getMessage());
		}

	}



	@Test
	public void addScheduleLine_NegativeTest_unknownUser() {
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0] = "f";
		days[1] = "f";
		days[2] = "f";
		days[5] = "f";
		days[6] = "true";
		days[3] = "true";
		days[4] = "true";
		try {
			assertNotNull(controller.addUserScheduledLine("b", "rer", "10:56", "11:05", days, "aa"));
			assertFalse(true);
		} catch (Exception e) {
			assertTrue("User was not found".equals(e.getMessage()));
		}

	}


	@Test
	public void addScheduleLine_NegativeTest1() {
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0] = "f";
		days[1] = "f";
		days[2] = "f";
		days[5] = "f";
		days[6] = "true";
		days[3] = "true";
		days[4] = "true";
		AuthentificationController controller1 = new AuthentificationController();

		try {
			controller1.registerUser("azaz", "aaa@gmail.com", "0769168018", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		int size = 0;
		try {
			assertTrue(controller.addUserScheduledLine("2", "metro", "10:56", "11:05", days, "azaz").size() == 3);
			size = DAOFactory.userScheduledLineDAO().selectAll().size();
		} catch (Exception e) {
			Assert.fail("add schedule line error : " + e.getMessage());
		}
		try {
			assertTrue(controller.addUserScheduledLine("2", "metro", "10:56", "11:05", days, "azaz").size() == 0);
			assertTrue(size == DAOFactory.userScheduledLineDAO().selectAll().size());
		} catch (Exception e) {
			Assert.fail("add schedule line error : " + e.getMessage());
		}
	}

	@Test
	public void addScheduleLine_NegativeTest2() {
		AuthentificationController controller1 = new AuthentificationController();
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0] = "f";
		days[1] = "f";
		days[2] = "f";
		days[5] = "f";
		days[6] = "f";
		days[3] = "f";
		days[4] = "f";
		try {
			controller1.registerUser("azahz", "aa5a@gmail.com", "0769868018", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}

		try {
			assertNotNull(controller.addUserScheduledLine("2", "metro", "10:56", "11:05", days, "azaz"));
			assertFalse(true);
		} catch (Exception e) {
			// e.printStackTrace(System.out);
			assertTrue("Invalid day infos".equals(e.getMessage()));
		}
	}

	@Test
	public void addScheduleLine_NegativeTest3() {
		AuthentificationController controller1 = new AuthentificationController();
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0] = "f";
		days[1] = "f";
		days[2] = "f";
		days[5] = "f";
		days[6] = "true";
		days[3] = "true";
		days[4] = "true";

		try {
			controller1.registerUser("azazg", "aaay@gmail.com", "0769168618", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}

		try {
			assertNotNull(controller.addUserScheduledLine("2", "rr", "10:56", "11:05", days, "azaz"));
			assertFalse(true);
		} catch (Exception e) {
			assertTrue("Invalid line type".equals(e.getMessage()));
		}
	}

	@Test
	public void addScheduleLine_NegativeTest4() {
		AuthentificationController controller1 = new AuthentificationController();
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0] = "f";
		days[1] = "f";
		days[2] = "f";
		days[5] = "f";
		days[6] = "true";
		days[3] = "true";
		days[4] = "true";

		try {
			controller1.registerUser("azazt", "aaa6@gmail.com", "0769148018", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}

		try {
			assertNotNull(controller.addUserScheduledLine("2", "rer", "10:60", "11:05", days, "azaz"));
			assertFalse(true);
		} catch (Exception e) {
			assertTrue("Time is invalid".equals(e.getMessage()));
		}
	}

	@Test
	public void addScheduleLine_NegativeTest5() {
		AuthentificationController controller1 = new AuthentificationController();
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0] = "f";
		days[1] = "f";
		days[2] = "f";
		days[5] = "f";
		days[6] = "true";
		days[3] = "true";
		days[4] = "true";

		try {
			controller1.registerUser("azaztt", "aaass6@gmail.com", "0769188018", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}

		try {
			assertNotNull(controller.addUserScheduledLine("2", "rer", "11:05", "10:55", days, "azaztt"));
			assertFalse(true);
		} catch (Exception e) {
			assertTrue("Time is invalid".equals(e.getMessage()));
		}
	}

	@Test
	public void addScheduleLine_NegativeTest6() {
		AuthentificationController controller1 = new AuthentificationController();
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0] = "f";
		days[1] = "f";
		days[2] = "f";
		days[5] = "f";
		days[6] = "true";
		days[3] = "true";
		days[4] = "true";

		try {
			controller1.registerUser("azazttt", "aaatss6@gmail.com", "0769188068", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}

		try {
			assertNotNull(controller.addUserScheduledLine("2", "rer", "11:0", "10:59", days, "azazttt"));
			assertFalse(true);
		} catch (Exception e) {
			assertTrue("Time is invalid".equals(e.getMessage()));
		}
	}

	@Test
	public void addScheduleLine_NegativeTest7() {
		AuthentificationController controller1 = new AuthentificationController();
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0] = "f";
		days[1] = "f";
		days[2] = "f";
		days[5] = "f";
		days[6] = "true";
		days[3] = "true";
		days[4] = "true";

		try {
			controller1.registerUser("azeazttt", "aaatss6e@gmail.com", "0769188078", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}

		try {

			assertNotNull(controller.addUserScheduledLine("2", "rer", "11:05:4", "11:08", days, "azeazttt"));
			assertFalse(true);
		} catch (Exception e) {

			assertTrue("Time is invalid".equals(e.getMessage()));
		}
	}

	@Test
	public void modifyScheduleLine_PositiveTest() {

		try{
			ScheduledLineController controller = new ScheduledLineController();
			List<UserScheduledLine> a = controller.setOfUserScheduledLine("addScheduleLine_PositiveTest");
			for (UserScheduledLine t : a) {
				System.out.println(t);
				
			}
		}catch (Exception e) {
			e.printStackTrace(System.out);
		}

	}




	//
	// @Test
	// public void addScheduleLine_NegativeTest2_emptyInfos() {
	// ScheduledLineController controller = new ScheduledLineController();
	// int[] minute = new int[2];
	// int[] hour = new int[2];
	// minute[0] = 4;
	// minute[1] = 8;
	// hour[0] = 9;
	// hour[0] = 4;
	//
	//
	// try {
	// assertNotNull(controller.addScheduledLine("b", "", minute, hour, "friday",
	// "aa"));
	// } catch (Exception e) {
	// assertTrue("Not enough infos".equals(e.getMessage()));
	// }
	// }
	//
	// @Test
	// public void addScheduleLine_NegativeTest3_emptyInfos() {
	// int[] minute = new int[2];
	// int[] hour = new int[2];
	// minute[0] = 4;
	// minute[1] = 8;
	// hour[0] = 9;
	// hour[0] = 4;
	//
	// ScheduledLineController controller = new ScheduledLineController();
	// try {
	// assertNotNull(controller.addScheduledLine("", "rer", minute, hour, "friday",
	// "aa"));
	// } catch (Exception e) {
	// assertTrue("Not enough infos".equals(e.getMessage()));
	// }
	// }
	//
	// @Test
	// public void addScheduleLine_NegativeTest4_emptyInfos() {
	// int[] minute = new int[2];
	// int[] hour = new int[2];
	// minute[0] = 4;
	// minute[1] = 8;
	// hour[0] = 9;
	// hour[0] = 4;
	// ScheduledLineController controller = new ScheduledLineController();
	// try {
	// assertNotNull(controller.addScheduledLine("b", "rer", minute, hour, "",
	// "aa"));
	// } catch (Exception e) {
	// assertTrue("Not enough infos".equals(e.getMessage()));
	// }
	// }
	//
	// @Test
	// public void addScheduleLine_NegativeTest5_emptyInfos() {
	// int[] minute = new int[2];
	// int[] hour = new int[2];
	// minute[0] = 4;
	// minute[1] = 8;
	// hour[0] = 9;
	// hour[0] = 4;
	// ScheduledLineController controller = new ScheduledLineController();
	// try {
	// assertNotNull(controller.addScheduledLine("b", "rer", minute, hour, "friday",
	// ""));
	// } catch (Exception e) {
	// assertTrue("User name is empty".equals(e.getMessage()));
	// }
	// }
	//

}
