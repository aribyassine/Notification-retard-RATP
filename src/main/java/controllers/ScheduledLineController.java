package controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.entities.Line;
import model.entities.Line.LineType;
import model.entities.UserScheduledLine;
import model.entities.UserScheduledLine.Day;
import model.entities.User;

/**
 * @author Ayyoub LABIB
 */

public class ScheduledLineController {

	public Set<UserScheduledLine> addUserScheduledLine(String LineName, String type, String debut, String fin, String[] days,
			String userName) throws DataException {

		if (userName.isEmpty())
			throw new DataException("User name is empty");

		if (LineName.isEmpty() || type.isEmpty() || debut.isEmpty() || fin.isEmpty() || days.length != 7)
			throw new DataException("Not enough infos");

		String timeDebut[] = debut.split(":");
		if (timeDebut.length != 2)
			throw new DataException("Time is invalid");

		String timeFin[] = fin.split(":");
		if (timeFin.length != 2)
			throw new DataException("Time is invalid");

		int hourDebut = 0;
		int minuteDebut = 0;
		int hourfin = 0;
		int minuteFin = 0;

		try {
			hourDebut = Integer.parseInt(timeDebut[0]);
			minuteDebut = Integer.parseInt(timeDebut[1]);
			hourfin = Integer.parseInt(timeFin[0]);
			minuteFin = Integer.parseInt(timeFin[1]);
		} catch (Exception e) {
			throw new DataException("Time is invalid");
		}

		if (hourfin < 0 || hourfin > 23 || hourDebut < 0 || hourDebut > 23 || minuteDebut < 0 || minuteDebut > 59
				|| minuteFin < 0 || minuteFin > 59)
			throw new DataException("Time is invalid");

		if (hourDebut == hourfin) {
			if (minuteDebut >= minuteFin)
				throw new DataException("Time is invalid");
		} else if (hourDebut > hourfin)
			throw new DataException("Time is invalid");

		LineType linetype;

		switch (type.toLowerCase()) {
		case "rer":
			linetype = LineType.rer;
			break;
		case "metro":
			linetype = LineType.metro;
			break;
		case "bus":
			linetype = LineType.bus;
			break;
		case "transilien":
			linetype = LineType.transilien;
			break;
		case "tramway":
			linetype = LineType.tramway;
			break;
		default:
			throw new DataException("Invalid line type");
		}

		ArrayList<Day> _days = new ArrayList<>();
		for (int k = 0; k < days.length; k++) {

			if (days[k].matches("true")) {
				switch (k) {
				case 6:
					_days.add(Day.sunday);
					break;
				case 0:
					_days.add(Day.monday);
					break;
				case 1:
					_days.add(Day.tuesday);
					break;
				case 2:
					_days.add(Day.wednesday);
					break;
				case 3:
					_days.add(Day.thursday);
					break;
				case 4:
					_days.add(Day.friday);
					break;
				case 5:
					_days.add(Day.saturday);
					break;
				default:
					throw new DataException("Server broke down");
				}
			}
		}

		if (_days.isEmpty())
			throw new DataException("Invalid day infos");

		User user = DAOFactory.userDAO().getByName(userName);
		if (user == null)
			throw new DataException("User was not found");

		Line line = DAOFactory.lineDAO().getByNameNType(LineName, linetype);
		if (line == null) {
			line = new Line();
			line.setLineName(LineName);
			line.setLineType(linetype);
			DAOFactory.lineDAO().save(line);
		}

		LocalTime begin = LocalTime.of(hourDebut, minuteDebut);
		LocalTime end = LocalTime.of(hourfin, minuteFin);

		Set<UserScheduledLine> result = new LinkedHashSet<>();
		for (Day day : _days) {

			// TODO : verifier chevauchement !!
			UserScheduledLine usl = DAOFactory.userScheduledLineDAO().getUserScheduledLineByAllInfos(line, user, begin,
					end, day);

			if (usl != null)
				continue;

			usl = new UserScheduledLine();
			usl.setBeginTime(begin);
			usl.setEndTime(end);
			usl.setDay(day);
			usl.setUser(user);
			usl.setLine(line);
			result.add(usl);
			DAOFactory.userScheduledLineDAO().save(usl);
		}

		return result;

	}

}
