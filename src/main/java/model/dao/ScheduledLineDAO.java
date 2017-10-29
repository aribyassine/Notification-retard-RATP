package model.dao;

import model.dao.interfaces.IDAO;
import model.entities.Line;
import model.entities.Schedule;
import model.entities.ScheduledLine;

/**
 * @author Mohamed T. KASSAR
 *
 */
public class ScheduledLineDAO extends DAO<ScheduledLine> implements IDAO<ScheduledLine> {

	public ScheduledLine getScheduledLineByObjects(Line line,Schedule schedule ) {
		return findOne(new String[] {"line", "schedule"}, new Object[] {line, schedule});
	}
}
