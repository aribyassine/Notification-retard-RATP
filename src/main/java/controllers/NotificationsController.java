package controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;


import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.entities.Line;
import model.entities.Notification;

/**
 * @author Mohamed T. KASSAR
 */

public class NotificationsController {

	public Notification addNotification(String message, Line l) throws DataException {

		if (message.isEmpty())
			throw new DataException("message is empty");

		Line tmp = DAOFactory.lineDAO().getByNameNType(l.getLineName(), l.getLineType());
		if (tmp == null)
			throw new DataException("Line is invalid");

		//TODO: test 30 min request
		Notification notif = new Notification();

		notif.setDate(LocalDateTime.now(ZoneId.of("GMT+01:00")));
		notif.setNotificationText(message);
		notif.setLine(l);

		DAOFactory.notificationDAO().save(notif);
		return notif;
	}
}
