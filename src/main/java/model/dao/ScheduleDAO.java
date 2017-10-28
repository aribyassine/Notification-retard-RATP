package model.dao;

import model.dao.interfaces.IDAO;
import model.entities.Schedule;
import model.entities.Schedule.Day;

/**
 * @author Mohamed T. KASSAR
 *
 */
public class ScheduleDAO extends DAO<Schedule> implements IDAO<Schedule> {

	//TODO
	public Schedule getBySchedule(int hour, int minute, Day day) {
		return null;
	}
}
