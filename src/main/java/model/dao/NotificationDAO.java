package model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.dao.interfaces.IDAO;
import model.entities.Line;
import model.entities.Notification;
import util.HibernateUtil;

/**
 * @author Mohamed t. KASSAR
 *
 */
public class NotificationDAO extends DAO<Notification> implements IDAO<Notification> {

	public Notification getLatestNotificationForLine(Line line){
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		s.beginTransaction();
		Notification result;
		List<?> list = s.getNamedQuery("getLatestNotificationForLine").setParameter("line", line).list();
		result = list.isEmpty() ? null : (Notification) list.get(0);
		s.getTransaction().commit();
		s.close();
		return result;
	}
}
