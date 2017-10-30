package controllers;

import java.util.Date;

import model.dao.DAOFactory;
import model.entities.Notification;
import model.entities.ScheduledLine;

/**
 * @author Ayyoub LABIB
 */

public class NotificationsController {
	
	public Notification addNotification(String message, ScheduledLine sl) {
		Notification notif = new Notification();
		notif.setDate(new Date());
		notif.setNotificationText(message);
		notif.setScheduledLine(sl);
		
		DAOFactory.notificationDAO().save(notif);
		return notif;
	}
}
