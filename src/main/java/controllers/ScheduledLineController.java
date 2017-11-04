package controllers;

import java.util.ArrayList;

import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.entities.Line;
import model.entities.Line.LineType;
import model.entities.Schedule;
import model.entities.Schedule.Day;
import model.entities.UserScheduledLine;
import model.entities.User;
import model.entities.UserScheduledLine;

/**
 * @author Ayyoub LABIB
 */

public class ScheduledLineController {

	public UserScheduledLine addUserScheduledLine(String LineName, String type, String debut, String fin, String[] days,
			String userName) throws DataException {

		if (userName.isEmpty())
			throw new DataException("User name is empty");

		if (LineName.isEmpty() || type.isEmpty() || debut.isEmpty() || fin.isEmpty())
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

		ArrayList<Day> d = new ArrayList<>();
		int g = 0;
		for (int k = 0; k < days.length; k++) {

			if (days[k].matches("true")) {
				g = 1;
				switch (k) {
				case 6:
					d.add(Day.sunday);
					break;
				case 0:
					d.add(Day.monday);
					break;
				case 1:
					d.add(Day.tuesday);
					break;
				case 2:
					d.add(Day.wednesday);
					break;
				case 3:
					d.add(Day.thursday);
					break;
				case 4:
					d.add(Day.friday);
					break;
				case 5:
					d.add(Day.saturday);
					break;
				default:
					throw new DataException("Server broke down");
				}
			}
		}
		if (g == 0)
			throw new DataException("Invalid day infos");

		User user = DAOFactory.userDAO().getByName(userName);

		if (user == null)
			throw new DataException("User was not found");

		System.out.println("05");
		Line line = DAOFactory.lineDAO().getByNameNType(LineName, linetype);
		if (line == null) {
			line = new Line();
			line.setLineName(LineName);
			line.setLineType(linetype);
			DAOFactory.lineDAO().save(line);
		}

	
		UserScheduledLine scheduledLine;
		UserScheduledLine userSL = null;
//		scheduledLine = DAOFactory.scheduledLineDAO().getScheduledLineByObjects(line, );
		
		for (int i = 0; i < schedule.length; i++) {
			
			if (scheduledLine == null) {

				scheduledLine = new UserScheduledLine();
				scheduledLine.setLine(line);
				scheduledLine.setSchedule(schedule[i]);
				DAOFactory.scheduledLineDAO().save(scheduledLine);

				userSL = new UserScheduledLine();
				userSL.setUser(user);
				userSL.setScheduledLine(scheduledLine);
				DAOFactory.userScheduledLineDAO().save(userSL);
			} else {
				userSL = DAOFactory.userScheduledLineDAO().getUserScheduledLineByScheduledLineNUser(scheduledLine, user);
				if (userSL != null) {
					throw new DataException("User scheduled line already exists");
				}
				userSL = new UserScheduledLine();
				userSL.setUser(user);
				userSL.setScheduledLine(scheduledLine);
				DAOFactory.userScheduledLineDAO().save(userSL);
			}
		}

		System.out.println("08");

		return userSL;

	}

}
