package controllers;

import java.time.LocalDate;
import java.time.ZoneId;

import model.dao.DAOFactory;
import model.entities.Notification;
import model.entities.ScheduledLine;

/**
 * @author Ayyoub LABIB
 */

public class NotificationsController {
	
	public Notification addNotification(String message, ScheduledLine sl) {
		Notification notif = new Notification();
		notif.setDate(LocalDate.now(ZoneId.of("GMT+01:00")));
		notif.setNotificationText(message);
		notif.setScheduledLine(sl);
		
		DAOFactory.notificationDAO().save(notif);
		return notif;
	}
}
