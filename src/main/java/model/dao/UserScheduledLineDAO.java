package model.dao;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.dao.interfaces.IDAO;
import model.entities.UserScheduledLine;
import model.entities.UserScheduledLine.Day;
import util.Converter;
import util.HibernateUtil;
import model.entities.Line;
import model.entities.User;

/**
 * @author Mohamed T. KASSAR
 *
 */
public class UserScheduledLineDAO extends DAO<UserScheduledLine> implements IDAO<UserScheduledLine> {

	public UserScheduledLine getUserScheduledLineByLineNUser(Line line, User user) {
		return findOne(new String[] { "line", "user" }, new Object[] { line, user });
	}
	
	public UserScheduledLine getUserScheduledLineByLineNUserNDay(Line line, User user, Day day) {
		return findOne(new String[] { "line", "user", "day" }, new Object[] { line, user, day });
	}

	public UserScheduledLine getUserScheduledLineByAllInfos(Line line, User user, LocalTime begin, LocalTime end,
			Day day) {
		return findOne(new String[] { "line", "user", "beginTime", "endTime", "day" },
				new Object[] { line, user, Converter.localTimeToDate(begin), Converter.localTimeToDate(end), day });
	}

	public Set<UserScheduledLine> getSchedulesByTime(int hour, int minute, Day day) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		s.beginTransaction();
		Set<UserScheduledLine> result = null;
		List<?> list = s.getNamedQuery("getUserScheduledLineByTime")
				.setParameter("time", Converter.localTimeToDate(LocalTime.of(hour, minute))).setParameter("day", day)
				.list();
		result = list.stream().map(sc -> (UserScheduledLine) sc).collect(Collectors.toSet());
		s.getTransaction().commit();
		s.close();
		return result;
	}

}
