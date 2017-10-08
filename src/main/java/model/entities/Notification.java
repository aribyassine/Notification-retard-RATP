package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

/**
 * @author Mohamed T. KASSAR
 *
 */

@Entity
@Table(name = "NOTIFICATION")
public class Notification implements Serializable { // TODO : remove notificationId and make scheduledLine as ID

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NOTIFICATION_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int notificationId;

	@ManyToOne
	@JoinColumn(name = "SCHEDULED_LINE")
	private ScheduledLine scheduledLine;

	@Temporal(TemporalType.DATE)
	@Column(name = "NOTIFICATION_DATE")
	private Date date;

	@Column(name = "NOTIFICATION_TEXT")
	private String notificationText;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "notification")
	private List<Comment> comments;
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public ScheduledLine getScheduledLine() {
		return scheduledLine;
	}

	public void setScheduledLine(ScheduledLine scheduledLine) {
		this.scheduledLine = scheduledLine;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNotificationText() {
		return notificationText;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}
}
