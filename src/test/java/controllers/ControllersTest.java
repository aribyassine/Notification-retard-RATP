package controllers;

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
			controller.registerUser("tiko", "ktarek1994@gmail.com", "0769128018", "051094");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		
		try {
			assertTrue(controller.checkLogin("tiko", "051094"));
		} catch (Exception e) {
			Assert.fail("Login error : " + e.getMessage());
		}
		
	}
	
}
