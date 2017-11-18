package controllers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.entities.Line;
import model.entities.Notification;
import model.entities.User;
import model.entities.UserNotification;

/**
 * @author Mohamed T. KASSAR
 */

public class NotificationsController {

	public Notification addNotification(String message, Line l) throws DataException {

		if (message == null || message.isEmpty())
			throw new DataException("message is empty");

		if (l == null)
			throw new DataException("Line is invalid");

		Line tmp = DAOFactory.lineDAO().getByNameNType(l.getLineName(), l.getLineType());
		if (tmp == null)
			throw new DataException("Line is invalid");

		Notification notif = new Notification();

		notif.setDate(LocalDateTime.now(ZoneId.of("GMT+01:00")));
		notif.setNotificationText(message);
		notif.setLine(l);

		DAOFactory.notificationDAO().save(notif);
		return notif;
	}

	public UserNotification addUserNotification(User user, Notification notification) throws DataException {

		if (user == null || !DAOFactory.userDAO().isExist(user))
			throw new DataException("User is invalid");

		if (notification == null)
			throw new DataException("Notification is invalid");

		UserNotification un = new UserNotification();
		un.setDate(LocalDateTime.now(ZoneId.of("GMT+01:00")));
		un.setNotification(notification);
		un.setUser(user);

		DAOFactory.userNotificationDAO().save(un);

		return un;
	}

	public Notification getLatestNotificationForLine(Line line) throws DataException {
		if (line == null || !DAOFactory.lineDAO().isExist(line))
			throw new DataException("Line is invalid");
		return DAOFactory.notificationDAO().getLatestNotificationForLine(line);
	}
	
	public Notification getNotificationById(Serializable id) throws DataException {
		Notification no = null;
		if(id==null || (no=DAOFactory.notificationDAO().getById(id))==null)
			throw new DataException("Notifications is not found");
		return no;
			
	}
	

}
