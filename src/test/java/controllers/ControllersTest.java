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
}
