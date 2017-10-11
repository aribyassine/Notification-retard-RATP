package model.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.dao.interfaces.IDAO;
import model.entities.User;
import util.HibernateUtil;

/**
 * @author Mohamed T. KASSAR
 *
 */
public class UserDAO extends DAO<User> implements IDAO<User> {

	public User getByName(String userName) {
		String jpql = "select t from " + User.class.getSimpleName() + " t where userName = :x";

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		Query q = s.createQuery(jpql);
		q.setParameter("x", userName);

		User entity = q.list().size() == 0 ? null : (User) q.list().get(0);
		s.close();

		return entity;
	}
}
