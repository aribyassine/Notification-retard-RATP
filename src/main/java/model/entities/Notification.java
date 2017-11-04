package model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import util.Converter;

/**
 * @author Mohamed T. KASSAR
 *
 */

@Entity
@Table(name = "NOTIFICATION")
public class Notification implements Serializable { 
	
	// remove notificationId and make scheduledLine as ID
	// NOT TODO : NO, we can't make scheduledLine ad ID, a scheduledLine may have many notifications
	// On the other hand we can make scheduledLine and date as a compound key but it is DJEBDA in hibernate

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NOTIFICATION_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int notificationId;

	@ManyToOne
	@JoinColumn(name = "LINE")
	private Line line;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "NOTIFICATION_DATE")
	private Date date;

	@Column(name = "NOTIFICATION_TEXT")
	private String notificationText;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "notification")
	private Set<Comment> comments;
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "notification")
	private Set<UserNotification> users;
	
	public Set<Comment> getComments() {
		return comments;
	}
	
	public Set<UserNotification> getUsersNotification() {
		return users;
	}
	
	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public Line getLine() {
		return line;
	}
	
	public void setLine(Line line) {
		this.line = line;
	}
	
	public LocalDateTime getDate() {
		return Converter.dateToLocalDateTime(date);
	}

	public void setDate(LocalDateTime date) {
		this.date = Converter.localDateTimeToDate(date);
	}

	public String getNotificationText() {
		return notificationText;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}

	@Override
	public String toString() {
		return "Notification{" +
				"notificationId=" + notificationId +
//				", scheduledLine=" + scheduledLine + TODO
				", date=" + date +
				", notificationText='" + notificationText + '\'' +
				", comments=" + comments +
				'}';
	}
}
