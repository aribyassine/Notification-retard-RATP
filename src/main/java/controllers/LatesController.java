package controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.dao.DAOFactory;
import model.entities.Line;
import model.entities.Notification;
import model.entities.Schedule;
import model.entities.Schedule.Day;
import model.entities.ScheduledLine;
import model.entities.User;
import model.entities.UserScheduledLine;

/**
 * @author Mohamed T. KASSAR
 */

public class LatesController {

	protected void checkForLates() {
		LocalTime localTime = LocalTime.now(ZoneId.of("GMT+01:00"));
		LocalDate localDate = LocalDate.now(ZoneId.of("GMT+01:00"));
		Day day = null;

		switch (localDate.getDayOfWeek().getValue()) {
		case 1:
			day = Day.monday;
			break;
		case 2:
			day = Day.tuesday;
			break;
		case 3:
			day = Day.wednesday;
			break;
		case 4:
			day = Day.thursday;
			break;
		case 5:
			day = Day.friday;
			break;
		case 6:
			day = Day.saturday;
			break;
		case 7:
			day = Day.sunday;
			break;
		}

		Schedule schedule = DAOFactory.scheduleDAO().getBySchedule(localTime.getHour(), localTime.getMinute(), day);
		if (schedule == null)
			return;

		Set<ScheduledLine> scheduledLines = schedule.getScheduledLines();

		scheduledLines.forEach(sl -> {
			Set<UserScheduledLine> affectedUsers = sl.getAffectedUsers();

			if (!affectedUsers.isEmpty()) {
				Set<User> users = filterUsers(
						affectedUsers.stream().map(usl -> usl.getUser()).collect(Collectors.toSet()), sl, localDate);

				if (users.isEmpty())
					return;

				if (thereAreLatesForLine(sl.getLine())) {
					users.forEach(user -> {
						notifyUser(user, sl.getLine());
					});
				}
			}
		});
	}

	private Set<User> filterUsers(Set<User> users, ScheduledLine sl, LocalDate date) {
		// TODO je vire les utilisateurs qui ont re�u une notification pour cette ligne
		// durant les 6 derni�res schedule (30 min**) pour cette ligne (sl), PS: code KHMADJ si vous arrivez � l'am�liorer mere7ba
		
		// j'ai mis 7 et non pas 6 car je v�rifie le schedule courant aussi, en cas o� !!! 
		for (int i = 0; i < 7; i++) {
			Set<Notification> todaysNotifications = sl.getNotifications().stream()
					.filter(notification -> notification.getDate().equals(date)).collect(Collectors.toSet());
			
			if (todaysNotifications.isEmpty())
				continue;

			sl.getAffectedUsers().forEach(au -> {
				users.remove(au.getUser());
			});

			int hour = sl.getSchedule().getHour();
			int minute = sl.getSchedule().getMinute();

			if (minute == 0) {
				minute = 55;
				hour--;
				if (hour < 0)
					return users;
			}

			sl = DAOFactory.scheduledLineDAO().getScheduledLineByObjects(sl.getLine(),
					DAOFactory.scheduleDAO().getBySchedule(hour, minute, sl.getSchedule().getDay()));
		}
		return users;
	}

	private boolean thereAreLatesForLine(Line line) {
		// TODO check in RATP
		return false;
	}

	private void notifyUser(User user, Line line) {
		String message = null; // TODO : generate message

		// TODO save notification in the database
		sendMail(user.getEmail(), decorateEmailMessage(message), line);
		sendSMS(user.getPhoneNumber(), message, line);
	}

	private static String decorateEmailMessage(String message) {
		//TODO add prefix and postfix 
		return message;
	}
	
	private static void sendSMS(String phoneNumber, String message, Line line) {
		// TODO intercept error and try to send with another server

		String url = "http://smsgateway.me/api/v3/messages/send?"
				+ "email=labib.ayyoub@gmail.com&password=abc123456&device=64842&number=" + phoneNumber + "&message="
				+ message;

		URL obj;
		try {
			obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void sendMail(String email, String mailText, Line line) {
		try {
			String host = "smtp.gmail.com";
			String user = "mailnotification12@gmail.com"; // TODO : to be changed
			String pass = "mailnotification";
			String to = email;
			String from = "RATP-NOTIFICATIONS";
			String subject = "NoReply - Late notification for " + line.getLineType() + " " + line.getLineName();
			String messageText = mailText;
			boolean sessionDebug = false;

			Properties props = System.getProperties();

			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.required", "true");

			Session mailSession = Session.getDefaultInstance(props, null);
			mailSession.setDebug(sessionDebug);
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(messageText);

			Transport transport = mailSession.getTransport("smtp");
			transport.connect(host, user, pass);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

}
