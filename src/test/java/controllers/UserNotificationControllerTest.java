package controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

public class UserNotificationControllerTest {
	
	
	@Test
	public void userRegestrationAndLogInPositiveTest() {
		UserNotificationsController controller = new UserNotificationsController();
		try {
			System.out.println(controller.getUserNotifications("addScheduleLine_PositiveTest"));
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}

		
	}

}
