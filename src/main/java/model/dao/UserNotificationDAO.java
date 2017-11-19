package model.dao;

import java.util.List;

import model.entities.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.dao.interfaces.IDAO;
import model.entities.Line;
import model.entities.Notification;
import model.entities.ClientNotification;
import util.HibernateUtil;

/**
 * @author Mohamed T. KASSAR
 *
 */

public class UserNotificationDAO extends DAO<ClientNotification> implements IDAO<ClientNotification> {

	public ClientNotification getLatestNotificationForUserNLine(Line line, Client client) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		s.beginTransaction();
		List<?> list = s.createSQLQuery("select n1.* from notification as n, client_notification as n1 where n.line = "
				+ line.getLineId() + " and n1.notification = n.notification_id and n1.client='"
				+ client.getUserName() + "' order by n1.client_notification_date desc limit 1").addEntity(ClientNotification.class).list();
		if (list.isEmpty())
			return null;

//		result = list.stream().map(o -> (Notification) o).collect(Collectors.toList()).stream().max((n1, n2) -> {
//			if (n1.getDate().isAfter(n2.getDate()))
//				return 1;
//			if (n2.getDate().isAfter(n1.getDate()))
//				return -1;
//			return 0;
//		}).get();

		s.getTransaction().commit();
		s.close();
		return (ClientNotification) list.get(0);
	}
	
	public List<Notification> getUserNotifications(String user) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		s.beginTransaction();
		List<Notification> list = s.createSQLQuery("select n.* from notification as n, client_notification as n1 where n1.client='"
				+ user + "' and  n.notification_id=n1.notification order by n1.client_notification_date desc").addEntity(Notification.class).list();
		if (list.isEmpty())
			return null;

//		result = list.stream().map(o -> (Notification) o).collect(Collectors.toList()).stream().max((n1, n2) -> {
//			if (n1.getDate().isAfter(n2.getDate()))
//				return 1;
//			if (n2.getDate().isAfter(n1.getDate()))
//				return -1;
//			return 0;
//		}).get();

		s.getTransaction().commit();
		s.close();
		return  list;
	}


}
