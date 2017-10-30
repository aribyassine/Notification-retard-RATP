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
		String[] days = new String[7];
		days[0]="f";
		days[1]="f";
		days[2]="f";
		days[5]="f";
		days[6]="true";
		days[3]="true";
		days[4]="true";
		AuthentificationController controller1 = new AuthentificationController();
		try {
			controller1.registerUser("tikooo", "fzefsfzdfd@gmail.com", "0764125018", "123lkj");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		
		try {
			assertNotNull(controller.addScheduledLine("b", "rer", "10:56", "11:05", days, "tikooo"));
		} catch (Exception e) {
			e.printStackTrace(System.out);
			Assert.fail("add schedule line error : " + e.getMessage());
		}

	}

//
//	@Test
//	public void addScheduleLine_PositiveTest1() {
//		int[] minute = new int[2];
//		int[] hour = new int[2];
//		minute[0] = 4;
//		minute[1] = 8;
//		hour[0] = 9;
//		hour[0] = 4;
//		ScheduledLineController controller = new ScheduledLineController();
//		AuthentificationController controller1 = new AuthentificationController();
//		try {
//			controller1.registerUser("tikoo", "fzefzdfd@gmail.com", "0769125018", "tikotiko");
//		} catch (Exception e) {
//			Assert.fail("Regestration error : " + e.getMessage());
//		}
//
//		try {
//			assertNotNull(controller.addScheduledLine("b", "rer", minute, hour, "friday", "tikoo"));
//		} catch (Exception e) {
//			Assert.fail("add schedule line error : " + e.getMessage());
//		}
//
//	}
//
	
	
	@Test
	public void addScheduleLine_NegativeTest_unknownUser() {
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0]="f";
		days[1]="f";
		days[2]="f";
		days[5]="f";
		days[6]="true";
		days[3]="true";
		days[4]="true";
		try {
			assertNotNull(controller.addScheduledLine("b", "rer","10:56", "11:05",days, "aa"));
		} catch (Exception e) {
			assertTrue("User was not found".equals(e.getMessage()));
		}

	}
//
	@Test
	public void addScheduleLine_NegativeTest1() {
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0]="f";
		days[1]="f";
		days[2]="f";
		days[5]="f";
		days[6]="true";
		days[3]="true";
		days[4]="true";
		AuthentificationController controller1 = new AuthentificationController();
		
		
		try {
			controller1.registerUser("azaz", "aaa@gmail.com", "0769168018", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		
		try {
			assertNotNull(controller.addScheduledLine("2", "metro", "10:56", "11:05", days, "azaz"));
		} catch (Exception e) {
			Assert.fail("add schedule line error : " + e.getMessage());
		}
		try {
			assertNotNull(controller.addScheduledLine("2", "metro","10:56", "11:05", days,"azaz"));

		}
		catch (Exception e) {
			assertTrue("User scheduled line already exists".equals(e.getMessage()));
		}
	}

	@Test
	public void addScheduleLine_NegativeTest2() {
		AuthentificationController controller1 = new AuthentificationController();
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0]="f";
		days[1]="f";
		days[2]="f";
		days[5]="f";
		days[6]="f";
		days[3]="f";
		days[4]="f";
		try {
			controller1.registerUser("azahz", "aa5a@gmail.com", "0769868018", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		
	
		try {
			assertNotNull(controller.addScheduledLine("2", "metro", "10:56", "11:05", days, "azaz"));

		}
		catch (Exception e) {
			//e.printStackTrace(System.out);
			assertTrue("Invalid day infos".equals(e.getMessage()));
		}
	}
	
	@Test
	public void addScheduleLine_NegativeTest3() {
		AuthentificationController controller1 = new AuthentificationController();
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0]="f";
		days[1]="f";
		days[2]="f";
		days[5]="f";
		days[6]="true";
		days[3]="true";
		days[4]="true";
		
		try {
			controller1.registerUser("azazg", "aaay@gmail.com", "0769168618", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		
	
		try {
			assertNotNull(controller.addScheduledLine("2", "rr","10:56", "11:05", days,"azaz"));

		}
		catch (Exception e) {
			assertTrue("Invalid line type".equals(e.getMessage()));
		}
	}
	
	@Test
	public void addScheduleLine_NegativeTest4() {
		AuthentificationController controller1 = new AuthentificationController();
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0]="f";
		days[1]="f";
		days[2]="f";
		days[5]="f";
		days[6]="true";
		days[3]="true";
		days[4]="true";
		
		
		try {
			controller1.registerUser("azazt", "aaa6@gmail.com", "0769148018", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		
	
		try {
			assertNotNull(controller.addScheduledLine("2", "rer", "10:60", "11:05", days, "azaz"));

		}
		catch (Exception e) {
			assertTrue("Time is invalid".equals(e.getMessage()));
		}
	}
	
	@Test
	public void addScheduleLine_NegativeTest5() {
		AuthentificationController controller1 = new AuthentificationController();
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0]="f";
		days[1]="f";
		days[2]="f";
		days[5]="f";
		days[6]="true";
		days[3]="true";
		days[4]="true";
		
		
		try {
			controller1.registerUser("azaztt", "aaass6@gmail.com", "0769188018", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		
	
		try {
			assertNotNull(controller.addScheduledLine("2", "rer", "11:05", "10:55", days, "azaztt"));

		}
		catch (Exception e) {
			assertTrue("Time is invalid".equals(e.getMessage()));
		}
	}
	
	@Test
	public void addScheduleLine_NegativeTest6() {
		AuthentificationController controller1 = new AuthentificationController();
		ScheduledLineController controller = new ScheduledLineController();
		String[] days = new String[7];
		days[0]="f";
		days[1]="f";
		days[2]="f";
		days[5]="f";
		days[6]="true";
		days[3]="true";
		days[4]="true";
		
		
		try {
			controller1.registerUser("azazttt", "aaatss6@gmail.com", "0769188068", "tikotiko");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		
	
		try {
			assertNotNull(controller.addScheduledLine("2", "rer", "11:05", "11:02", days, "azazttt"));

		}
		catch (Exception e) {
			assertTrue("Time is invalid".equals(e.getMessage()));
		}
	}
	
//	
//	@Test
//	public void addScheduleLine_NegativeTest2_emptyInfos() {
//		ScheduledLineController controller = new ScheduledLineController();
//		int[] minute = new int[2];
//		int[] hour = new int[2];
//		minute[0] = 4;
//		minute[1] = 8;
//		hour[0] = 9;
//		hour[0] = 4;
//	
//	
//		try {
//			assertNotNull(controller.addScheduledLine("b", "", minute, hour, "friday", "aa"));
//		} catch (Exception e) {
//			assertTrue("Not enough infos".equals(e.getMessage()));
//		}
//	}
//
//	@Test
//	public void addScheduleLine_NegativeTest3_emptyInfos() {
//		int[] minute = new int[2];
//		int[] hour = new int[2];
//		minute[0] = 4;
//		minute[1] = 8;
//		hour[0] = 9;
//		hour[0] = 4;
//	
//		ScheduledLineController controller = new ScheduledLineController();
//		try {
//			assertNotNull(controller.addScheduledLine("", "rer", minute, hour, "friday", "aa"));
//		} catch (Exception e) {
//			assertTrue("Not enough infos".equals(e.getMessage()));
//		}
//	}
//
//	@Test
//	public void addScheduleLine_NegativeTest4_emptyInfos() {
//		int[] minute = new int[2];
//		int[] hour = new int[2];
//		minute[0] = 4;
//		minute[1] = 8;
//		hour[0] = 9;
//		hour[0] = 4;
//		ScheduledLineController controller = new ScheduledLineController();
//		try {
//			assertNotNull(controller.addScheduledLine("b", "rer", minute, hour, "", "aa"));
//		} catch (Exception e) {
//			assertTrue("Not enough infos".equals(e.getMessage()));
//		}	
//	}
//
//	@Test
//	public void addScheduleLine_NegativeTest5_emptyInfos() {
//		int[] minute = new int[2];
//		int[] hour = new int[2];
//		minute[0] = 4;
//		minute[1] = 8;
//		hour[0] = 9;
//		hour[0] = 4;
//		ScheduledLineController controller = new ScheduledLineController();
//		try {
//			assertNotNull(controller.addScheduledLine("b", "rer", minute, hour, "friday", ""));
//		} catch (Exception e) {
//			assertTrue("User name is empty".equals(e.getMessage()));
//		}
//	}
//





}
