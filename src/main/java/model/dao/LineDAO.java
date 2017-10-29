package model.dao;

import model.dao.interfaces.IDAO;
import model.entities.Line;
import model.entities.Line.LineType;

/**
 * @author Mohamed T. KASSAR
 *
 */
public class LineDAO extends DAO<Line> implements IDAO<Line> {
	
	public Line getByNameNType(String name, LineType type) {
		return findOne(new String[] {"lineName", "lineType"}, new Object[] {name, type});
	}
	
}
