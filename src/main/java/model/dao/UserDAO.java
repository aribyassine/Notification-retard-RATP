package model.dao;

import model.entities.Client;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.dao.interfaces.IDAO;
import util.HibernateUtil;

/**
 * @author Mohamed T. KASSAR
 *
 */
public class UserDAO extends DAO<Client> implements IDAO<Client> {

	public Client getByName(String userName) {
//		String jpql = "select t from " + Client.class.getSimpleName() + " t where userName = :x";
//
//		SessionFactory factory = HibernateUtil.getSessionFactory();
//		Session s = factory.openSession();
//		Query q = s.createQuery(jpql);
//		q.setParameter("x", userName);
//
//		Client entity = q.list().size() == 0 ? null : (Client) q.list().get(0);
//		s.close();

		return DAOFactory.userDAO().getById(userName);
	}
	
	public Client getByMail(String email) {
		String jpql = "select t from " + Client.class.getSimpleName() + " t where email = :x";

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		Query q = s.createQuery(jpql);
		q.setParameter("x", email);

		Client entity = q.list().size() == 0 ? null : (Client) q.list().get(0);
		s.close();

		return entity;
	}
	
	public Client getByPhoneNumber(String phoneNumber) {
		String jpql = "select t from " + Client.class.getSimpleName() + " t where phoneNumber = :x";

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		Query q = s.createQuery(jpql);
		q.setParameter("x", phoneNumber);

		Client entity = q.list().size() == 0 ? null : (Client) q.list().get(0);
		s.close();

		return entity;
	}
	
	public boolean isExist(Client client) {
		Client temp = getByName(client.getUserName());
		return temp != null;
	}
}
