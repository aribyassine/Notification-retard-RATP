package model.dao;

import model.dao.interfaces.IDAO;
import model.entities.ScheduledLine;
import model.entities.User;
import model.entities.UserScheduledLine;

/**
 * @author Mohamed T. KASSAR
 *
 */
public class UserScheduledLineDAO extends DAO<UserScheduledLine> implements IDAO<UserScheduledLine> {
	
	public UserScheduledLine getUserScheduledLineByScheduledLineNUser(ScheduledLine scheduledLine, User user ) {
		return findOne(new String[] {"scheduledLine", "user"}, new Object[] {scheduledLine, user});
	}
}
