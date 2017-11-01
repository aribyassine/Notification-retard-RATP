package controllers;

import java.util.ArrayList;

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

	public User addScheduledLine(String LineName, String type, String debut , String fin, String[] days, String userName) throws DataException {


		if (userName.isEmpty())
			throw new DataException("User name is empty");
		


		if (LineName.isEmpty() || type.isEmpty()  || debut.isEmpty() || fin.isEmpty())
			throw new DataException("Not enough infos");

		String timeDebut [] =  debut.split(":");
		if(timeDebut.length>2)
			throw new DataException("Time is invalid");

		String timeFin [] =  fin.split(":");
		if(timeFin.length>2)
			throw new DataException("Time is invalid");
		int hourDebut = 0 ;
		int minuteDebut = 0;
		int hourfin = 0;
		int minuteFin = 0 ;

		try {
			hourDebut = Integer.parseInt(timeDebut[0]);
			minuteDebut = Integer.parseInt(timeDebut[1]);
			hourfin = Integer.parseInt(timeFin[0]);
			minuteFin = Integer.parseInt(timeFin[1]);
		}
		catch(Exception e) {
			throw new DataException("Time is invalid");
		}
		if(hourfin<0 || hourfin > 23 || hourDebut<0 || hourfin> 23 || minuteDebut<0 || minuteDebut > 59 || minuteFin<0 || minuteFin >59 )
			throw new DataException("Time is invalid");
		
			if(hourDebut == hourfin) {
				if(minuteDebut>=minuteFin)
					throw new DataException("Time is invalid");
			}
			else if(hourDebut>hourfin)
				throw new DataException("Time is invalid");
		
		

		ArrayList<Integer> minute = new ArrayList<>();
		ArrayList<Integer> hour = new ArrayList<>();


		int j=0;
		minute.add(minuteDebut%60);

		hour.add(hourDebut%24);

		if(minute.get(j)>=55)
			hour.add((hour.get(j)+1)%24);
		else
			hour.add((hour.get(j))%24);


		minute.add((minute.get(j++)+5) %60);




		while( ( minute.get(j) < minuteFin  && hour.get(j) <= hourfin ) ||
				(minute.get(j) > minuteFin && hour.get(j) < hourfin)) {

			if(minute.get(j)>=55)
				hour.add((hour.get(j)+1)%24);
			else
				hour.add((hour.get(j))%24);
			
			minute.add((minute.get(j++)+5) %60);



		}
		System.out.println("hour=" + hour.toString());
		System.out.println("minute=" + minute.toString());


		for (int i = 0; i < hour.size(); i++) {
			if (minute.get(i) >= 60 || minute.get(i) < 0 || hour.get(i) >= 24 || hour.get(i) < 0)
				throw new DataException("Time is invalid");
		}



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
		int g=0;
		for (int k = 0; k < days.length; k++) {

			if(days[k].matches("true")) {
				g=1;
				switch (k) {
				case 6:
					d.add(Day.sunday);
					break;
				case 0:
					d.add (Day.monday);
					break;
				case 1:
					d.add( Day.tuesday);
					break;
				case 2:
					d.add (Day.wednesday);
					break;
				case 3:
					d.add (Day.thursday);
					break;
				case 4:
					d.add(Day.friday);
					break;
				case 5:
					d.add( Day.saturday);
					break;
				default:
					throw new DataException("Server broke down");
				}
			}
		}

		if(g==0)
			throw new DataException("Invalid day infos");

		User user = DAOFactory.userDAO().getByName(userName);

		if (user == null)
			throw new DataException("User was not found");




		Line line= 	DAOFactory.lineDAO().getByNameNType(LineName, linetype); 
		if(line==null) {
			line= new Line();
			line.setLineName(LineName);
			line.setLineType(linetype);
			DAOFactory.lineDAO().save(line); 
		}

		Schedule schedule[] = new Schedule[hour.size()*d.size()];
		int z=0;
		for (int i = 0; i < hour.size(); i++) {
			for (int k = 0; k < d.size(); k++) {

				schedule[z]=DAOFactory.scheduleDAO().getBySchedule(hour.get(i), minute.get(i), d.get(k));
				if(schedule[z]==null) {
					schedule[z] = new Schedule();
					schedule[z].setDay(d.get(k));
					schedule[z].setHour(hour.get(i));
					schedule[z].setMinute(minute.get(i));
					//					System.out.println(hour.get(i).toString());
					//					System.out.println(minute.get(i) - minute.get(i)%5 );
					//					System.out.println(minute.get(i));
					//					System.out.println(schedule[z].getMinute());
					//					System.out.println(schedule[z].toString());

					DAOFactory.scheduleDAO().save(schedule[z]);
				}
				z++;

			}



		}
		ScheduledLine s ;
		UserScheduledLine userSL ;
		for (int i = 0; i < schedule.length; i++) {
			s= DAOFactory.scheduledLineDAO().getScheduledLineByObjects(line, schedule[i]);
			if(s==null) {

				s=new ScheduledLine();
				s.setLine(line);
				s.setSchedule(schedule[i]);
				DAOFactory.scheduledLineDAO().save(s);

				userSL= new UserScheduledLine();
				userSL.setUser(user);
				userSL.setScheduledLine(s);
				DAOFactory.userScheduledLineDAO().save(userSL);
			}
			else {
				userSL=DAOFactory.userScheduledLineDAO().getUserScheduledLineByScheduledLineNUser(s, user);
				if(userSL != null) {
					throw new DataException("User scheduled line already exists");
				}
				userSL= new UserScheduledLine();
				userSL.setUser(user);
				userSL.setScheduledLine(s);
				DAOFactory.userScheduledLineDAO().save(userSL);
			}
		}



		return user;

	}

}
