package model.dao;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import model.entities.Client;
import model.entities.ClientScheduledLine;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.dao.interfaces.IDAO;
import model.entities.ClientScheduledLine.Day;
import util.Converter;
import util.HibernateUtil;
import model.entities.Line;

/**
 * @author Mohamed T. KASSAR
 *
 */
public class UserScheduledLineDAO extends DAO<ClientScheduledLine> implements IDAO<ClientScheduledLine> {

	public ClientScheduledLine getUserScheduledLineByLineNUser(Line line, Client client) {
		return findOne(new String[] { "line", "client" }, new Object[] { line, client});
	}

	

	public ClientScheduledLine getUserScheduledLineByAllInfos(Line line, Client client, LocalTime begin, LocalTime end,
                                                              Day day) {
		return findOne(new String[] { "line", "client", "beginTime", "endTime", "day" },
				new Object[] { line, client, Converter.localTimeToDate(begin), Converter.localTimeToDate(end), day });
	}

	public Set<ClientScheduledLine> getSchedulesByTime(int hour, int minute, Day day) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		s.beginTransaction();
		Set<ClientScheduledLine> result = null;
		List<?> list = s.getNamedQuery("getClientScheduledLineByTime")
				.setParameter("time", Converter.localTimeToDate(LocalTime.of(hour, minute))).setParameter("day", day)
				.list();
		result = list.stream().map(sc -> (ClientScheduledLine) sc).collect(Collectors.toSet());
		s.getTransaction().commit();
		s.close();
		return result;
	}

	public List<ClientScheduledLine> getListOfUserScheduledLinesOfUser(String user) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session s = factory.openSession();
		s.beginTransaction();
		@SuppressWarnings("unchecked")
		List<ClientScheduledLine> list = s.createSQLQuery("select *  from client_scheduled_line "
				+ "  where client ='" + user+"'").addEntity(ClientScheduledLine.class).list();
		if (list.isEmpty())
			return null;

		s.getTransaction().commit();
		s.close();
		return list;
	}

}
