package model.dao;

/**
 * @author Mohamed T. KASSAR
 *
 */
public class DAOFactory {

	private static LineDAO lineDAO;
	private static UserDAO userDAO;
	private static NotificationDAO notificationDAO;
	private static UserScheduledLineDAO userScheduledLineDAO;
	private static CommentDAO commentDAO;
	private static UserNotificationDAO userNotificationDAO;

	public static LineDAO lineDAO() {
		if (lineDAO == null)
			lineDAO = new LineDAO();
		return lineDAO;
	}



	public static UserDAO userDAO() {
		if (userDAO == null)
			userDAO = new UserDAO();
		return userDAO;
	}

	public static NotificationDAO notificationDAO() {
		if (notificationDAO == null)
			notificationDAO = new NotificationDAO();
		return notificationDAO;
	}

	public static UserScheduledLineDAO userScheduledLineDAO() {
		if (userScheduledLineDAO == null)
			userScheduledLineDAO = new UserScheduledLineDAO();
		return userScheduledLineDAO;
	}

	public static CommentDAO commentDAO() {
		if (commentDAO == null)
			commentDAO = new CommentDAO();
		return commentDAO;
	}

	public static UserNotificationDAO userNotificationDAO() {
		if (userNotificationDAO == null)
			userNotificationDAO = new UserNotificationDAO();
		return userNotificationDAO;
	}
}
