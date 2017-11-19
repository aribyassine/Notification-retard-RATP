package controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.entities.Client;
import model.entities.ClientScheduledLine;
import model.entities.Line;
import model.entities.Line.LineType;
import model.entities.ClientScheduledLine.Day;

/**
 * @author Ayyoub LABIB
 */

public class ScheduledLineController {

	public Set<ClientScheduledLine> getAllUserScheduledLine(String userName){
		Client client = DAOFactory.userDAO().getByName(userName);
		return client.getScheduledLines();
	}

	public Set<ClientScheduledLine> addUserScheduledLine(String LineName, String type, String debut, String fin,
                                                         String[] days, String userName) throws DataException {

		if (userName.isEmpty())
			throw new DataException("Client name is empty");

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
		case "rers":
			linetype = LineType.rer;
			break;
		case "metros":
			linetype = LineType.metro;
			break;
		case "bus":
			linetype = LineType.bus;
			break;
		case "noctiliens":
			linetype = LineType.transilien;
			break;
		case "tramways":
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

		Client client = DAOFactory.userDAO().getByName(userName);
		if (client == null)
			throw new DataException("Client was not found");

		Line line = DAOFactory.lineDAO().getByNameNType(LineName, linetype);
		if (line == null) {
			line = new Line();
			line.setLineName(LineName);
			line.setLineType(linetype);
			DAOFactory.lineDAO().save(line);
		}

		LocalTime begin = LocalTime.of(hourDebut, minuteDebut);
		LocalTime end = LocalTime.of(hourfin, minuteFin);

		Set<ClientScheduledLine> result = new LinkedHashSet<>();
		for (Day day : _days) {

			// TODO : verifier chevauchement !!
			ClientScheduledLine usl = DAOFactory.userScheduledLineDAO().getUserScheduledLineByAllInfos(line, client, begin,
					end, day);

			if (usl != null)
				continue;

			// usl =
			// DAOFactory.userScheduledLineDAO().getUserScheduledLineByLineNUserNDay(line,
			// client,day);

			// if(usl!=null && usl.getDay() == day) {
			// System.out.println("if(usl!=null && usl.getDay().equals(day))");
			// LocalTime b = usl.getBeginTime();
			// LocalTime e = usl.getEndTime();
			// if(begin.isBefore(b)) {
			// DAOFactory.userScheduledLineDAO().remove(usl);
			// if(end.isAfter(e)) {
			// usl = new ClientScheduledLine();
			// usl.setBeginTime(begin);
			// usl.setEndTime(end);
			// usl.setDay(day);
			// usl.setClient(client);
			// usl.setLine(line);
			// result.add(usl);
			// DAOFactory.userScheduledLineDAO().save(usl);
			// }
			// else {
			// usl = new ClientScheduledLine();
			// usl.setBeginTime(begin);
			// usl.setEndTime(e);
			// usl.setDay(day);
			// usl.setClient(client);
			// usl.setLine(line);
			// result.add(usl);
			// DAOFactory.userScheduledLineDAO().save(usl);
			//
			// }
			// }
			// else if(b.isBefore(begin)) {
			// if(end.isAfter(e)) {
			// DAOFactory.userScheduledLineDAO().remove(usl);
			// usl = new ClientScheduledLine();
			// usl.setBeginTime(b);
			// usl.setEndTime(end);
			// usl.setDay(day);
			// usl.setClient(client);
			// usl.setLine(line);
			// result.add(usl);
			// DAOFactory.userScheduledLineDAO().save(usl);
			// }
			// else
			// continue;
			// }
			// else {
			// if(end.isAfter(e)) {
			// DAOFactory.userScheduledLineDAO().remove(usl);
			// usl = new ClientScheduledLine();
			// usl.setBeginTime(b);
			// usl.setEndTime(end);
			// usl.setDay(day);
			// usl.setClient(client);
			// usl.setLine(line);
			// result.add(usl);
			// DAOFactory.userScheduledLineDAO().save(usl);
			// }
			// else
			// continue;
			//
			//
			// }
			//
			//
			// }

			usl = new ClientScheduledLine();
			usl.setBeginTime(begin);
			usl.setEndTime(end);
			usl.setDay(day);
			usl.setClient(client);
			usl.setLine(line);
			result.add(usl);
			DAOFactory.userScheduledLineDAO().save(usl);
		}

		return result;

	}

	public Set<ClientScheduledLine> modifyUserScheduledLine(int id, String LineName, String type, String debut,
                                                            String fin, String[] days, String userName) throws DataException {
		if (!deleteUserScheduledLine(id))
			throw new DataException("Client schedule line doesn't exist");
		return (addUserScheduledLine(LineName, type, debut, fin, days, userName));

	}

	public boolean deleteUserScheduledLine(int id) throws DataException {
		ClientScheduledLine tmp = DAOFactory.userScheduledLineDAO().getById(id);
		if (tmp != null) {
			DAOFactory.userScheduledLineDAO().remove(tmp);
			return true;
		} else
			return false;
	}

	public List<ClientScheduledLine> setOfUserScheduledLine(String username) throws DataException {
		if (username.isEmpty() || DAOFactory.userDAO().getById(username)==null)
			throw new DataException("Client is invalid");
		List<ClientScheduledLine> a = DAOFactory.userScheduledLineDAO().getListOfUserScheduledLinesOfUser(username);
		return a;
	}

}
