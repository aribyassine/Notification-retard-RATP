package controllers;

import static org.junit.Assert.assertNotNull;

import model.entities.ClientScheduledLine;
import org.junit.Assert;
import org.junit.Test;

import model.entities.Line;
import model.entities.Line.LineType;

public class NotificationControllerTest {
	
	
	

	@Test
	public void NotificationCntroller_PositiveTest() {
		NotificationsController controller = new NotificationsController();
		ScheduledLineController controller2 = new ScheduledLineController();
		AuthentificationController controller1 = new AuthentificationController();
		
		String[] days = new String[7];
		days[0]="f";
		days[1]="f";
		days[2]="f";
		days[5]="f";
		days[6]="true";
		days[3]="true";
		days[4]="true";
		
		try {
			controller1.registerUser("tikooo", "fzefsfzdfd@gmail.com", "0764125018", "123lkj");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		ClientScheduledLine tmp = null;
		try {
			assertNotNull(tmp=(controller2.addUserScheduledLine("b", "rer", "10:00", "10:04", days, "tikooo").toArray(new ClientScheduledLine[0])[0]));
		} catch (Exception e) {
			Assert.fail("add schedule line error : " + e.getMessage());
		}
		try {
			controller.addNotification("Test 01", tmp.getLine());
		} catch (Exception e) {
			Assert.fail("Add Notification error : " + e.getMessage());
		}

	}
	
	@Test
	public void NotificationController_NegativeTest1() {
		NotificationsController controller = new NotificationsController();
	
		Line tmp1 = new Line();
		tmp1.setLineName("1");
		tmp1.setLineType(LineType.metro);
		
		try {
			controller.addNotification("Test 02", tmp1);
		} catch (Exception e) {
			Assert.assertTrue("Line is invalid".equals(e.getMessage()));
		}

	}
	
	@Test
	public void NotificationController_NegativeTest2() {
		NotificationsController controller = new NotificationsController();
		ScheduledLineController controller2 = new ScheduledLineController();
		AuthentificationController controller1 = new AuthentificationController();
		
		String[] days = new String[7];
		days[0]="f";
		days[1]="f";
		days[2]="f";
		days[5]="f";
		days[6]="true";
		days[3]="true";
		days[4]="true";
		
		try {
			controller1.registerUser("NotificationController_NegativeTest2", "fzefsfzfd@gmail.com", "0764125718", "123lkj");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		ClientScheduledLine tmp = null;
		try {
			assertNotNull(tmp=controller2.addUserScheduledLine("a", "rer", "10:00", "10:04", days, "NotificationController_NegativeTest2").toArray(new ClientScheduledLine[0])[0]);
		} catch (Exception e) {
			Assert.fail("add schedule line error : " + e.getMessage());
		}
		try {
			controller.addNotification("", tmp.getLine());
		} catch (Exception e) {
			Assert.assertTrue("message is empty".equals(e.getMessage()));		}

	}


	

}
