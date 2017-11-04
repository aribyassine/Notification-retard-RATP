//package controllers;
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.util.Date;
//import java.util.Properties;
//import java.util.Set;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//import controllers.exceptions.DataException;
//import model.dao.DAOFactory;
//import model.entities.Line;
//import model.entities.Line.LineType;
//import model.entities.Notification;
//import model.entities.Schedule;
//import model.entities.Schedule.Day;
//import model.entities.ScheduledLine;
//import model.entities.User;
//import model.entities.UserScheduledLine;
//
///**
// * @author Mohamed T. KASSAR
// */
//
//public class LatesController {
//
//	private static NotificationsController notificationsController = new NotificationsController();
//
//	public static void startLinesUpdater() {
//		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1, (Runnable r) -> {
//			Thread t = Executors.defaultThreadFactory().newThread(r);
//			t.setDaemon(true);
//			return t;
//		});
//
//		scheduler.scheduleAtFixedRate(() -> {
//
//			checkForLates();
//
//		}, 0, 5, TimeUnit.MINUTES);
//	}
//
//	protected static void checkForLates() {
//		LocalTime localTime = LocalTime.now(ZoneId.of("GMT+01:00"));
//		LocalDate localDate = LocalDate.now(ZoneId.of("GMT+01:00"));
//		Day day = null;
//
//		switch (localDate.getDayOfWeek().getValue()) {
//		case 1:
//			day = Day.monday;
//			break;
//		case 2:
//			day = Day.tuesday;
//			break;
//		case 3:
//			day = Day.wednesday;
//			break;
//		case 4:
//			day = Day.thursday;
//			break;
//		case 5:
//			day = Day.friday;
//			break;
//		case 6:
//			day = Day.saturday;
//			break;
//		case 7:
//			day = Day.sunday;
//			break;
//		}
//
//		System.out.println(localTime);
//		Set<Schedule> schedules = DAOFactory.scheduleDAO().getSchedulesByTime(localTime.getHour(), localTime.getMinute(), day);
//
//		System.out.println(schedule);
//		if (schedule == null)
//			return;
//
//		Set<ScheduledLine> scheduledLines = schedule.getScheduledLines();
//
//		System.out.println(scheduledLines);
//
//		if (scheduledLines == null)
//			return;
//
//		System.out.println(
//				"debut d'envoi des mails pour toutes les scheduledLines \n -----------------------------------------\n");
//
//		scheduledLines.forEach(sl -> {
//			Set<UserScheduledLine> affectedUsers = sl.getAffectedUsers();
//
//			if (affectedUsers == null)
//				return;
//
//			System.out.println("****  envoi pour la ligne : " +sl.getLine().getLineName());
//
//			if (!affectedUsers.isEmpty()) {
//
//				System.out.println();
//				System.out.println("start filter");
//				Set<User> users = filterUsers(
//						affectedUsers.stream().map(usl -> usl.getUser()).collect(Collectors.toSet()), sl, localDate);
//				System.out.println("end filter");
//				System.out.println();
//
//				if (users.isEmpty())
//					return;
//
//				System.out.println("start sending");
//				if (thereAreLatesForLine(sl.getLine())) {
//					users.forEach(user -> {
//						try {
//							notificationsController.addNotification("C'est un test", sl);
//						} catch (DataException e) {
//							e.printStackTrace();
//						}
//						System.out.println("\t envoi de mail à " + user.getUserName());
//						notifyUser(user, sl.getLine());
//					});
//				}
//				System.out.println("end sending");
//
//			}
//		});
//
//		System.out.println(
//				"fin d'envoi des mails pour toutes les scheduledLines \n -----------------------------------------\n");
//	}
//
//	private static Set<User> filterUsers(Set<User> users, ScheduledLine sl, LocalDate date) {
//		// TODO je vire les utilisateurs qui ont reçu une notification pour cette ligne
//		// durant les 6 dernières schedule (30 min**) pour cette ligne (sl), PS: code
//		// KHMADJ si vous arrivez à l'améliorer mere7ba
//
//		// j'ai mis 7 et non pas 6 car je vérifie le schedule courant aussi, en cas où
//		// !!!
////		for (int i = 0; i < 7; i++) {
////
////			Set<Notification> todaysNotifications = sl.getNotifications().stream()
////					.filter(notification -> notification.getDate().equals(date)).collect(Collectors.toSet());
////
////			if (todaysNotifications.isEmpty())
////				continue;
////
////			sl.getAffectedUsers().forEach(au -> {
////				users.remove(au.getUser());
////			});
////
////			int hour = sl.getSchedule().getHour();
////			int minute = sl.getSchedule().getMinute();
////
////			if (minute == 0) {
////				minute = 55;
////				hour--;
////				if (hour < 0)
////					return users;
////			}
////
////			sl = DAOFactory.scheduledLineDAO().getScheduledLineByObjects(sl.getLine(),
////					DAOFactory.scheduleDAO().getBySchedule(hour, minute, sl.getSchedule().getDay()));
////		}
//		return users;
//	}
//
//	private static boolean thereAreLatesForLine(Line line) {
//		// TODO check in RATP
//		return true;
//	}
//
//	private static void notifyUser(User user, Line line) {
//		String message = "C'est un test"; // TODO : generate message
//
//		// TODO save notification in the database
//		sendMail(user.getEmail(), decorateEmailMessage(message), line);
//		// sendSMS(user.getPhoneNumber(), message, line);
//	}
//
//	private static String decorateEmailMessage(String message) {
//		// TODO add prefix and postfix
//		return message;
//	}
//
//	private static void sendSMS(String phoneNumber, String message, Line line) {
//		// TODO intercept error and try to send with another server
//
//		String url = "http://smsgateway.me/api/v3/messages/send?"
//				+ "email=labib.ayyoub@gmail.com&password=abc123456&device=64842&number=" + phoneNumber + "&message="
//				+ message;
//
//		URL obj;
//		try {
//			obj = new URL(url);
//			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//			con.setRequestMethod("POST");
//			con.getResponseCode();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private static void sendMail(String email, String mailText, Line line) {
//		try {
//			String host = "smtp.gmail.com";
//			String user = "mailnotification12@gmail.com"; // TODO : to be changed
//			String pass = "mailnotification";
//			String to = email;
//			String from = "RATP-NOTIFICATIONS";
//			String subject = "NoReply - Late notification for " + line.getLineType() + " " + line.getLineName();
//			String messageText = mailText;
//			boolean sessionDebug = false;
//
//			Properties props = System.getProperties();
//
//			props.put("mail.smtp.starttls.enable", "true");
//			props.put("mail.smtp.host", host);
//			props.put("mail.smtp.port", "587");
//			props.put("mail.smtp.auth", "true");
//			props.put("mail.smtp.starttls.required", "true");
//
//			Session mailSession = Session.getDefaultInstance(props, null);
//			mailSession.setDebug(sessionDebug);
//			Message msg = new MimeMessage(mailSession);
//			msg.setFrom(new InternetAddress(from));
//			InternetAddress[] address = { new InternetAddress(to) };
//			msg.setRecipients(Message.RecipientType.TO, address);
//			msg.setSubject(subject);
//			msg.setSentDate(new Date());
//			msg.setText(messageText);
//
//			Transport transport = mailSession.getTransport("smtp");
//			transport.connect(host, user, pass);
//			transport.sendMessage(msg, msg.getAllRecipients());
//			transport.close();
//		} catch (Exception ex) {
//			System.out.println(ex);
//		}
//
//	}
//
//	public static void main(String[] args) throws DataException {
//		AuthentificationController ac = new AuthentificationController();
//
//		ac.registerUser("user0", "ktarek1994@gmail.com", "0769128018", "pwd01");
//		ac.registerUser("user1", "ktiko1994@yahoo.fr", "0769128020", "pwd02");
//		ac.registerUser("user2", "ktarek1994@outlook.com", "0769128019", "pwd03");
//		ac.registerUser("user3", "mtkassar@altirc.com", "0769128021", "pwd04");
//
//		ScheduledLineController slc = new ScheduledLineController();
//
//		System.out.println("DEBUT -------------------------------------");
//		slc.addUserScheduledLine("d", "rer", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user0");
//		System.out.println("ok");
//
//		slc.addUserScheduledLine("d", "rer", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user1");
//		System.out.println("ok");
//
//		
//		slc.addUserScheduledLine("a", "rer", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user0");
//
//		System.out.println("ok");
//
//		slc.addUserScheduledLine("a", "rer", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user1");
//
//		System.out.println("ok");
//
//		slc.addUserScheduledLine("a", "rer", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user2");
//
//		System.out.println("ok");
//
//		slc.addUserScheduledLine("8", "metro", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user0");
//		System.out.println("ok");
//
//		slc.addUserScheduledLine("8", "metro", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user1");
//
//		System.out.println("ok");
//
//		slc.addUserScheduledLine("8", "metro", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user2");
//
//		System.out.println("ok");
//
//		slc.addUserScheduledLine("8", "metro", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user3");
//		System.out.println("ok");
//
//		slc.addUserScheduledLine("15", "metro", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user0");
//		System.out.println("ok");
//
//		slc.addUserScheduledLine("15", "metro", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user1");
//		System.out.println("ok");
//
//		slc.addUserScheduledLine("15", "metro", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user2");
//		System.out.println("ok");
//
//		slc.addUserScheduledLine("15", "metro", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user3");
//		System.out.println("ok");
//
//		System.out.println("ok");
//
//		slc.addUserScheduledLine("9", "tramway", "11:20", "14:00",
//				new String[] { "true", "true", "true", "true", "true", "true", "true" }, "user0");
//
//		System.out.println("FIN -------------------------------------");
//
//		startLinesUpdater();
//	}
//
//}
