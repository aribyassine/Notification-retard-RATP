package model.dao;

/**
 * @author Mohamed T. KASSAR
 *
 */
public class DAOFactory {

	private static LineDAO lineDAO;
	private static ScheduleDAO scheduleDAO;
	private static ScheduledLineDAO scheduledLineDAO;
	private static UserDAO userDAO;
	private static NotificationDAO notificationDAO;
	private static UserScheduledLineDAO userScheduledLineDAO;
	private static CommentDAO commentDAO;

	public static LineDAO lineDAO() {
		if (lineDAO == null)
			lineDAO = new LineDAO();
		return lineDAO;
	}

	public static ScheduleDAO scheduleDAO() {
		if (scheduleDAO == null)
			scheduleDAO = new ScheduleDAO();
		return scheduleDAO;
	}

	public static ScheduledLineDAO scheduledLineDAO() {
		if (scheduledLineDAO == null)
			scheduledLineDAO = new ScheduledLineDAO();
		return scheduledLineDAO;
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
}
