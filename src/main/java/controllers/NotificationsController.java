package controllers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.entities.Client;
import model.entities.Line;
import model.entities.Notification;
import model.entities.ClientNotification;

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

	public ClientNotification addUserNotification(Client client, Notification notification) throws DataException {

		if (client == null || !DAOFactory.userDAO().isExist(client))
			throw new DataException("Client is invalid");

		if (notification == null)
			throw new DataException("Notification is invalid");

		ClientNotification un = new ClientNotification();
		un.setDate(LocalDateTime.now(ZoneId.of("GMT+01:00")));
		un.setNotification(notification);
		un.setClient(client);

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
