package controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Mohamed T. KASSAR
 *
 */

public class ControllersTest {

	@Test
	public void userRegestrationAndLogInPositiveTest() {
		AuthentificationController controller = new AuthentificationController();
		try {
			controller.registerUser("tiko", "ktarek1994@gmail.com", "0769128018", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}

		try {
			assertNotNull(controller.checkLogin("tiko", "tikotiko"));
		} catch (Exception e) {
			Assert.fail("Login error : " + e.getMessage());
		}

		try {
			assertNotNull(controller.checkLogin("tiko", "koukou"));
		} catch (Exception e) {
			assertTrue("Invalid user name or password".equals(e.getMessage()));
		}
	}

	@Test
	public void userRegestrationNegativeTest_emptyUserName() {
		AuthentificationController controller = new AuthentificationController();
		try {
			controller.registerUser("", "ktarek1994@gmail.com", "0769128018", "tikotiko");
		} catch (Exception e) {
			assertTrue("User name cannot be empty".equals(e.getMessage()));
		}
	}

	@Test
	public void userRegestrationNegativeTest_emptyPassword() {
		AuthentificationController controller = new AuthentificationController();
		try {
			controller.registerUser("holoulou", "ktarek1994@gmail.com", "0769128018", "");
		} catch (Exception e) {
			assertTrue("Password cannot be empty".equals(e.getMessage()));
		}
	}

	@Test
	public void userLogInNegativeTest_invalidPassword() {
		AuthentificationController controller = new AuthentificationController();
		try {
			assertNotNull(controller.registerUser("machin01", "machin01@gmail.com", "0769258596", "youyou"));
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
			e.printStackTrace();
		}

		try {
			assertNotNull(controller.checkLogin("machin01", "123458"));
		} catch (Exception e) {
			assertTrue("Invalid user name or password".equals(e.getMessage()));

		}
	}

	@Test
	public void userLogInNegativeTest_invalidUserName() {
		AuthentificationController controller = new AuthentificationController();
		try {
			assertNotNull(controller.checkLogin("machin", "123458"));
		} catch (Exception e) {
			assertTrue("Invalid user name or password".equals(e.getMessage()));
		}
	}

	@Test
	public void userRegestrationNegativeTest_usedMail() {
		AuthentificationController controller = new AuthentificationController();
		try {
			assertNotNull(controller.registerUser("ayyoub", "ayyoub@gmail.com", "0896325811", "youyou"));
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}

		try {
			assertNotNull(controller.registerUser("yacine", "ayyoub@gmail.com", "0852369712", "dzdzdz"));
		} catch (Exception e) {
			assertTrue("E-Mail already used".equals(e.getMessage()));
		}

	}

	@Test
	public void addScheduleLine_PositiveTest() {
		ScheduledLineController controller = new ScheduledLineController();
		try {
			assertNotNull(controller.addScheduledLine("b", "rer", 4, 4, "friday", "ayyoub"));
		} catch (Exception e) {
			Assert.fail("add schedule line error : " + e.getMessage());
		}

	}


	@Test
	public void addScheduleLine_PositiveTest1() {
		ScheduledLineController controller = new ScheduledLineController();
		AuthentificationController controller1 = new AuthentificationController();
		try {
			controller1.registerUser("tikoo", "fzefzdfd@gmail.com", "0769125018", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}

		try {
			assertNotNull(controller.addScheduledLine("b", "rer", 4, 4, "friday", "tikoo"));
		} catch (Exception e) {
			Assert.fail("add schedule line error : " + e.getMessage());
		}

	}

	@Test
	public void addScheduleLine_NegativeTest_unknownUser() {
		ScheduledLineController controller = new ScheduledLineController();
		try {
			assertNotNull(controller.addScheduledLine("b", "rer", 1, 2, "friday", "aa"));
		} catch (Exception e) {
			assertTrue("User was not found".equals(e.getMessage()));
		}

	}

	@Test
	public void addScheduleLine_NegativeTest1() {
		ScheduledLineController controller = new ScheduledLineController();
		AuthentificationController controller1 = new AuthentificationController();
		
		
		try {
			controller1.registerUser("azaz", "aaa@gmail.com", "0769168018", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		
		try {
			assertNotNull(controller.addScheduledLine("2", "metro", 1, 2, "friday", "azaz"));
		} catch (Exception e) {
			Assert.fail("add schedule line error : " + e.getMessage());
		}
		try {
			assertNotNull(controller.addScheduledLine("2", "metro", 1, 2, "friday", "azaz"));

		}
		catch (Exception e) {
			assertTrue("User scheduled line already exists".equals(e.getMessage()));
		}
	}

	@Test
	public void addScheduleLine_NegativeTest2() {
		ScheduledLineController controller = new ScheduledLineController();
		AuthentificationController controller1 = new AuthentificationController();
		
		
		try {
			controller1.registerUser("azahz", "aa5a@gmail.com", "0769868018", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		
	
		try {
			assertNotNull(controller.addScheduledLine("2", "metro", 5, 2, "f", "azaz"));

		}
		catch (Exception e) {
			assertTrue("Invalid day".equals(e.getMessage()));
		}
	}
	
	@Test
	public void addScheduleLine_NegativeTest3() {
		ScheduledLineController controller = new ScheduledLineController();
		AuthentificationController controller1 = new AuthentificationController();
		
		
		try {
			controller1.registerUser("azazg", "aaay@gmail.com", "0769168618", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		
	
		try {
			assertNotNull(controller.addScheduledLine("2", "rr", 6, 2, "friday", "azaz"));

		}
		catch (Exception e) {
			assertTrue("Invalid line type".equals(e.getMessage()));
		}
	}
	
	@Test
	public void addScheduleLine_NegativeTest4() {
		ScheduledLineController controller = new ScheduledLineController();
		AuthentificationController controller1 = new AuthentificationController();
		
		
		try {
			controller1.registerUser("azazt", "aaa6@gmail.com", "0769148018", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		
	
		try {
			assertNotNull(controller.addScheduledLine("2", "rer", 60, 2, "friday", "azaz"));

		}
		catch (Exception e) {
			assertTrue("Time is invalid".equals(e.getMessage()));
		}
	}
	
	
	@Test
	public void addScheduleLine_NegativeTest2_emptyInfos() {
		ScheduledLineController controller = new ScheduledLineController();
		try {
			assertNotNull(controller.addScheduledLine("b", "", 4, 4, "friday", "aa"));
		} catch (Exception e) {
			assertTrue("Not enough infos".equals(e.getMessage()));
		}
	}

	@Test
	public void addScheduleLine_NegativeTest3_emptyInfos() {
		ScheduledLineController controller = new ScheduledLineController();
		try {
			assertNotNull(controller.addScheduledLine("", "rer", 4, 4, "friday", "aa"));
		} catch (Exception e) {
			assertTrue("Not enough infos".equals(e.getMessage()));
		}
	}

	@Test
	public void addScheduleLine_NegativeTest4_emptyInfos() {
		ScheduledLineController controller = new ScheduledLineController();
		try {
			assertNotNull(controller.addScheduledLine("b", "rer", 4, 4, "", "aa"));
		} catch (Exception e) {
			assertTrue("Not enough infos".equals(e.getMessage()));
		}	
	}

	@Test
	public void addScheduleLine_NegativeTest5_emptyInfos() {
		ScheduledLineController controller = new ScheduledLineController();
		try {
			assertNotNull(controller.addScheduledLine("b", "rer", 4, 4, "friday", ""));
		} catch (Exception e) {
			assertTrue("User name is empty".equals(e.getMessage()));
		}
	}






}
