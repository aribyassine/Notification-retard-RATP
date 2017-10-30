package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Mohamed T. KASSAR
 *
 */

@Entity
@Table(name = "USER_SCHEDULED_LINE", uniqueConstraints = @UniqueConstraint(columnNames = { "SCHEDULED_LINE", "USER" }))
public class UserScheduledLine implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "USER_SCHEDULED_LINE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userScheduledLineId;

	@ManyToOne
	@JoinColumn(name = "USER")
	private User user;

	@ManyToOne
	@JoinColumn(name = "SCHEDULED_LINE")
	private ScheduledLine scheduledLine;

	public int getUserScheduledLineId() {
		return userScheduledLineId;
	}

	public void setUserScheduledLineId(int userScheduledLine) {
		this.userScheduledLineId = userScheduledLine;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ScheduledLine getScheduledLine() {
		return scheduledLine;
	}

	public void setScheduledLine(ScheduledLine scheduledLine) {
		this.scheduledLine = scheduledLine;
	}

	@Override
	public String toString() {
		return "UserScheduledLine{" +
				"userScheduledLineId=" + userScheduledLineId +
//				", user=" + user + TODO
//				", scheduledLine=" + scheduledLine + TODO
				'}';
	}
}
