package controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.entities.Line;
import model.entities.Notification;
import model.entities.User;
import model.entities.UserNotification;
import model.entities.UserScheduledLine;
import model.entities.UserScheduledLine.Day;

/**
 * @author Mohamed T. KASSAR
 */

public class LatesController {

	private static NotificationsController notificationsController = new NotificationsController();
	private static UserNotificationsController userNotificationsController = new UserNotificationsController();

	public static void startLinesUpdater() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1, (Runnable r) -> {
			Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setDaemon(false);
			return t;
		});

		scheduler.scheduleAtFixedRate(() -> {

			checkForLates();

		}, 0, 1, TimeUnit.MINUTES);
	}

	protected static void checkForLates() {
		LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("GMT+01:00"));
		Day day = null;

		switch (localDateTime.getDayOfWeek().getValue()) {
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

		System.out.println(localDateTime);
		Set<UserScheduledLine> usls = DAOFactory.userScheduledLineDAO().getSchedulesByTime(localDateTime.getHour(),
				localDateTime.getMinute(), day);

		if (usls.size() == 0)
			return;

		Map<Line, Set<User>> map = new HashMap<>();

		usls.forEach(usl -> {
			if (map.containsKey(usl.getLine())) {
				map.get(usl.getLine()).add(usl.getUser());
			} else {
				Set<User> temp = new LinkedHashSet<>();
				temp.add(usl.getUser());
				map.put(usl.getLine(), temp);
			}
		});

		map.entrySet().forEach(entry -> {
			Line line = entry.getKey();
			if (thereAreLatesForLine(line)) {
				Set<User> users = entry.getValue().stream().distinct().collect(Collectors.toSet());
				// get the last notification created for this line
				Notification notification = null;
				try {
					notification = notificationsController.getLatestNotificationForLine(line);
				} catch (DataException e1) {
					e1.printStackTrace();
					return;
				}

				if (notification != null) {
					if (notification.getDate().plusMinutes(120).isBefore(localDateTime)) {
						
						// the perturbation has exceeded 120 min
						// create a new notification
						try {
							notification = notificationsController.addNotification(generateNotificationText(), line);
						} catch (DataException e) {
							e.printStackTrace();
							return;
						}
					} else if (!notification.getNotificationText().equals("text"/* TODO */)) {
						// a new perturbation in less than 30 min and the perturbation is changed
						// create new notif. notify all
						try {
							notification = notificationsController.addNotification(generateNotificationText(), line);
						} catch (DataException e) {
							e.printStackTrace();
							return;
						}
						for (User user : users) {
							notifyUser(user, line, notification);
						}
						return;
					}

					for (User user : users) {
						UserNotification userLastNotification = null;
						try {
							userLastNotification = userNotificationsController.getLatestNotificationForUserNLine(line,
									user);
						} catch (DataException e) {
							e.printStackTrace();
							return;
						}
						if (userLastNotification == null
								|| userLastNotification.getDate().plusMinutes(30).isBefore(localDateTime)) {
							// Notify user
							notifyUser(user, line, notification);
						}

					}

				} else {
					// notify all
					try {
						notification = notificationsController.addNotification(generateNotificationText(), line);
					} catch (DataException e) {
						e.printStackTrace();
						return;
					}
					for (User user : users) {
						notifyUser(user, line, notification);
					}
				}
			}
		});

	}

	private static String generateNotificationText() {
		// TODO:
		return "text";
	}

	private static boolean thereAreLatesForLine(Line line) {
		// TODO check in RATP
		return true;
	}

	private static void notifyUser(User user, Line line, Notification notification) {
		try {
			userNotificationsController.addUserNotification(notification, user);
		} catch (DataException e) {
			e.printStackTrace();
			return;
		}
		System.err.println("notify : " + user.getEmail() + ", for line : "+ line.getLineName());
		//sendMail(user.getEmail(), decorateEmailMessage(notification.getNotificationText()), line);
	}

	private static String decorateEmailMessage(String message) {
		// TODO add prefix and postfix
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

	public static void main(String[] args) throws DataException {
		AuthentificationController ac = new AuthentificationController();

		ac.registerUser("user0", "ktarek1994@gmail.com", "0769128018", "pwd01");
		ac.registerUser("user1", "ktiko1994@yahoo.fr", "0769128020", "pwd02");
		ac.registerUser("user2", "ktarek1994@outlook.com", "0769128019", "pwd03");
		ac.registerUser("user3", "mtkassar@altirc.com", "0769128021", "pwd04");

		ScheduledLineController slc = new ScheduledLineController();

		slc.addUserScheduledLine("d", "rer", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user0");
		System.out.println("ok");

		slc.addUserScheduledLine("d", "rer", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user1");
		System.out.println("ok");

		slc.addUserScheduledLine("a", "rer", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user0");

		System.out.println("ok");

		slc.addUserScheduledLine("a", "rer", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user1");

		System.out.println("ok");

		slc.addUserScheduledLine("a", "rer", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user2");

		System.out.println("ok");

		slc.addUserScheduledLine("8", "metro", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user0");
		System.out.println("ok");

		slc.addUserScheduledLine("8", "metro", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user1");

		System.out.println("ok");

		slc.addUserScheduledLine("8", "metro", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user2");

		System.out.println("ok");

		slc.addUserScheduledLine("8", "metro", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user3");
		System.out.println("ok");

		slc.addUserScheduledLine("15", "metro", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user0");
		System.out.println("ok");

		slc.addUserScheduledLine("15", "metro", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user1");
		System.out.println("ok");

		slc.addUserScheduledLine("15", "metro", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user2");
		System.out.println("ok");

		slc.addUserScheduledLine("15", "metro", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user3");
		System.out.println("ok");

		System.out.println("ok");

		slc.addUserScheduledLine("9", "tramway", "00:00", "23:59",
				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user0");

		System.out.println("FIN -------------------------------------");

		startLinesUpdater();
	}

}
