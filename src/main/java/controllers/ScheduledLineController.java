package controllers;

import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.entities.Line;
import model.entities.Line.LineType;
import model.entities.Schedule;
import model.entities.Schedule.Day;
import model.entities.ScheduledLine;
import model.entities.User;
import model.entities.UserScheduledLine;

/**
 * @author Ayyoub LABIB
 */

public class ScheduledLineController {

	public User addScheduledLine(String LineName, String type, int minute, int hour, String day, String userName,
			Boolean isConnected) throws DataException {
		if (!isConnected)
			throw new DataException("User logged out");

		if (userName.isEmpty())
			throw new DataException("User name is empty");

		if (LineName.isEmpty() || type.isEmpty() || day.isEmpty())
			throw new DataException("Not enough infos");

		if (minute >= 60 || minute < 0 || hour >= 24 || hour < 0)
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
			linetype = null;
			break;
		}

		if (linetype == null)
			throw new DataException("Invalid line type");

		Day d;
		switch (day.toLowerCase()) {
		case "sunday":
			d = Day.sunday;
			break;
		case "monday":
			d = Day.monday;
			break;
		case "tuesday":
			d = Day.tuesday;
			break;
		case "wendesday":
			d = Day.wendesday;
			break;
		case "thursday":
			d = Day.thursday;
			break;
		case "friday":
			d = Day.friday;
			break;
		case "Saturday":
			d = Day.saturday;
			break;
		default:
			d = null;
			break;
		}

		if (d == null)
			throw new DataException("Invalid day");

		User user = DAOFactory.userDAO().getByName(userName);

		if (user == null)
			throw new DataException("User was not found");




		Line line= 	DAOFactory.lineDAO().getByName(LineName);
		if(line==null) {
			line= new Line();
			line.setLineName(LineName);
			line.setLineType(linetype);
			DAOFactory.lineDAO().save(line); 
		}
		Schedule schedule =DAOFactory.scheduleDAO().getBySchedule(hour, minute, d);
		if(schedule==null) {
			schedule = new Schedule();
			schedule.setDay(d);
			schedule.setHour(hour);
			schedule.setMinute(minute);
			DAOFactory.scheduleDAO().save(schedule);
		}
		
		ScheduledLine s = DAOFactory.scheduledLineDAO().getScheduledLineByObjects(line, schedule);
		UserScheduledLine userSL ;
		if(s==null) {
			s=new ScheduledLine();
			s.setLine(line);
			s.setSchedule(schedule);
			DAOFactory.scheduledLineDAO().save(s);
			
			userSL= new UserScheduledLine();
			userSL.setUser(user);
			userSL.setScheduledLine(s);
			DAOFactory.userScheduledLineDAO().save(userSL);
		}
		else {
			userSL=DAOFactory.userScheduledLineDAO().getUserScheduledLineByScheduledLine(s);
			if(userSL == null) {
				userSL= new UserScheduledLine();
				userSL.setUser(user);
				userSL.setScheduledLine(s);
				DAOFactory.userScheduledLineDAO().save(userSL);
			}
		}
	

	
		
		

		return user;

	}
}
