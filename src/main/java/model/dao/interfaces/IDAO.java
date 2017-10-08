package model.dao.interfaces;

import java.util.Set;

/**
 * @author Mohamed T. KASSAR
 *
 */

public interface IDAO<E> {

	void save(E entity);

	void remove(E entity);

	E getById(int id);

	E update(E entity);

	Set<E> selectAll();

	E findOne(String paramName, Object paramValue);

	E findOne(String[] paramNames, Object[] paramValues);

	// int findCountBy(String paramName, Object paramValue);
}
