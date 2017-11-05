package model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.dao.interfaces.IDAO;
import model.entities.Line;
import model.entities.User;
import model.entities.UserNotification;
import util.HibernateUtil;

/**
 * @author Mohamed T. KASSAR
 *
 */

public class UserNotificationDAO extends DAO<UserNotification> implements IDAO<UserNotification> {

	public UserNotification getLatestNotificationForUserNLine(Line line, User user) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		s.beginTransaction();
		List<?> list = s.createSQLQuery("select`n1`.* from notification as n, user_notification as n1 where `n`.`line` = "
				+ line.getLineId() + " and `n1`.`NOTIFICATION` = `n`.`NOTIFICATION_ID` and `n1`.`user`='"
				+ user.getUserName() + "' order by `n1`.`USER_NOTIFICATION_DATE` desc limit 1").addEntity(UserNotification.class).list();
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
		return (UserNotification) list.get(0);
	}

}
