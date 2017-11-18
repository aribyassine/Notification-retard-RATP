package controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
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

public class UserNotificationsController {

	public UserNotification addUserNotification(Notification notification, User user) throws DataException {

		if (user == null || !DAOFactory.userDAO().isExist(user))
			throw new DataException("User is invalid");
		UserNotification notif = new UserNotification();
		notif.setDate(LocalDateTime.now(ZoneId.of("GMT+01:00")));
		notif.setNotification(notification);
		notif.setUser(user);
		DAOFactory.userNotificationDAO().save(notif);
		return notif;
	}

	public UserNotification getLatestNotificationForUserNLine(Line line, User user) throws DataException {
		if (user == null || !DAOFactory.userDAO().isExist(user))
			throw new DataException("User is invalid");

		if (line == null || !DAOFactory.lineDAO().isExist(line))
			throw new DataException("Line is invalid");

		return DAOFactory.userNotificationDAO().getLatestNotificationForUserNLine(line, user);
	}
	
	
	public List<Notification> getUserNotifications(String User) throws DataException{
		if (User.isEmpty() || DAOFactory.userDAO().getById(User)==null)
			throw new DataException("User is invalid");
		
		
		return DAOFactory.userNotificationDAO().getUserNotifications(User);
		
	}

}
