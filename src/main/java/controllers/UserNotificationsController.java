package controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.entities.Client;
import model.entities.Line;
import model.entities.Notification;
import model.entities.ClientNotification;

/**
 * @author Mohamed T. KASSAR
 */

public class UserNotificationsController {

	public ClientNotification addUserNotification(Notification notification, Client client) throws DataException {

		if (client == null || !DAOFactory.userDAO().isExist(client))
			throw new DataException("Client is invalid");
		ClientNotification notif = new ClientNotification();
		notif.setDate(LocalDateTime.now(ZoneId.of("GMT+01:00")));
		notif.setNotification(notification);
		notif.setClient(client);
		DAOFactory.userNotificationDAO().save(notif);
		return notif;
	}

	public ClientNotification getLatestNotificationForUserNLine(Line line, Client client) throws DataException {
		if (client == null || !DAOFactory.userDAO().isExist(client))
			throw new DataException("Client is invalid");

		if (line == null || !DAOFactory.lineDAO().isExist(line))
			throw new DataException("Line is invalid");

		return DAOFactory.userNotificationDAO().getLatestNotificationForUserNLine(line, client);
	}


	public List<Notification> getUserNotifications(String user) throws DataException{
		return DAOFactory.userDAO().getByName(user).getUserNotifications().stream().map(ClientNotification::getNotification).collect(Collectors.toList());
	}

}
