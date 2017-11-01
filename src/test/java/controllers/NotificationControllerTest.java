package controllers;

import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;

import model.entities.Line;
import model.entities.Line.LineType;
import model.entities.Schedule;
import model.entities.Schedule.Day;
import model.entities.ScheduledLine;
import model.entities.UserScheduledLine;

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
		UserScheduledLine tmp = null;
		try {
			assertNotNull(tmp=controller2.addUserScheduledLine("b", "rer", "10:00", "10:04", days, "tikooo"));
		} catch (Exception e) {
			Assert.fail("add schedule line error : " + e.getMessage());
		}
		try {
			controller.addNotification("test", tmp.getScheduledLine());
		} catch (Exception e) {
			Assert.fail("Add Notification error : " + e.getMessage());
		}

	}
	
	@Test
	public void NotificationController_NegativeTest1() {
		NotificationsController controller = new NotificationsController();
	
		ScheduledLine tmp = new ScheduledLine();
		Line tmp1 = new Line();
		Schedule tmp2 = new Schedule();
		tmp1.setLineName("1");
		tmp1.setLineType(LineType.metro);
		tmp2.setDay(Day.friday);
		tmp2.setHour(13);
		tmp2.setMinute(10);
		tmp.setLine(tmp1);
		tmp.setSchedule(tmp2);
		
		try {
			controller.addNotification("test", tmp);
		} catch (Exception e) {
			Assert.assertTrue("Scheduled line is invalid ".equals(e.getMessage()));
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
			controller1.registerUser("tiko", "fzefsfzfd@gmail.com", "0764125718", "123lkj");
		} catch (Exception e) {
			Assert.fail("Regestration error : " + e.getMessage());
		}
		UserScheduledLine tmp = null;
		try {
			assertNotNull(tmp=controller2.addUserScheduledLine("a", "rer", "10:00", "10:04", days, "tiko"));
		} catch (Exception e) {
			Assert.fail("add schedule line error : " + e.getMessage());
		}
		try {
			controller.addNotification("", tmp.getScheduledLine());
		} catch (Exception e) {
			Assert.assertTrue("message is empty".equals(e.getMessage()));		}

	}


	

}
