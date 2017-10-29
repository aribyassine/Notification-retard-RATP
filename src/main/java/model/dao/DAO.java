package model.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.dao.interfaces.IDAO;
import util.HibernateUtil;

/**
 * @author Mohamed T. KASSAR
 *
 */

@SuppressWarnings("unchecked")
public class DAO<E> implements IDAO<E> {

	private final Class<E> clazz;

	public DAO() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		clazz = (Class<E>) type.getActualTypeArguments()[0];
	}

	@Override
	public void save(E entity) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		s.beginTransaction();
		s.save(entity);
		s.getTransaction().commit();
		s.close();
	}

	@Override
	public void remove(E entity) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		s.beginTransaction();
		s.delete(entity);
		s.getTransaction().commit();
		s.close();
	}

	@Override
	public E getById(int id) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		s.beginTransaction();
		E entity = (E) s.get(clazz, id);
		s.getTransaction().commit();
		s.close();
		return entity;
	}

	@Override
	public E update(E entity) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		s.beginTransaction();
		s.update(entity);
		s.getTransaction().commit();
		s.close();
		return null;
	}

	@Override
	public Set<E> selectAll() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		Query q = s.createQuery("from " + clazz.getSimpleName());
		Set<E> set = (Set<E>) q.list().stream().map(o -> (E) o).collect(Collectors.toSet());
		s.close();
		return set;
	}

	protected E findOne(String paramName, Object paramValue) {
		return findOne(new String[] { paramName }, new Object[] { paramValue });
	}

	protected E findOne(String[] paramNames, Object[] paramValues) {

		if (paramNames.length != paramValues.length)
			return null;

		String jpql = "select t from " + clazz.getSimpleName() + " t where ";
		for (int i = 0; i < paramNames.length; i++) {
			jpql += paramNames[i] + " = :x" + i + " ";
			if (i + 1 < paramNames.length)
				jpql += "and ";

		}

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		Query q = s.createQuery(jpql);
		
		for (int i = 0; i < paramNames.length; i++)
			q.setParameter("x" + i, paramValues[i]);
		
		E entity = q.list().size() == 0 ? null : (E) q.list().get(0);
		s.close();

		return entity;
	}
}
