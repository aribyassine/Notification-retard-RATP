package controllers;

import java.time.LocalDate;
import java.time.ZoneId;

import org.hibernate.TransientObjectException;

import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.entities.Notification;
import model.entities.UserScheduledLine;

/**
 * @author Mohamed Tarek KASSAR
 */

public class NotificationsController {

	public Notification addNotification(String message, UserScheduledLine sl) throws DataException {

		if(message.isEmpty())
			throw new DataException("message is empty");
		try {
			UserScheduledLine tmp = DAOFactory.scheduledLineDAO().getScheduledLineByObjects(sl.getLine(), sl.getSchedule());
			if(tmp==null)
				throw new DataException("Scheduled line is invalid ");
		}
		catch(TransientObjectException e) {
			throw new DataException("Scheduled line is invalid ");
		}

		Notification notif = new Notification();

		notif.setDate(LocalDate.now(ZoneId.of("GMT+01:00")));
		notif.setNotificationText(message);
		notif.setScheduledLine(sl);

		DAOFactory.notificationDAO().save(notif);
		return notif;
	}
}
