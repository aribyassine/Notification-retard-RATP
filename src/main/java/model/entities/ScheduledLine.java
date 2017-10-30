package model.entities;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;

/**
 * @author Mohamed T. KASSAR
 *
 */

@Entity
@Table(name = "SCHEDULED_LINE", uniqueConstraints = @UniqueConstraint(columnNames = { "SCHEDULE", "LINE" }))
public class ScheduledLine implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SCHEDULED_LINE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int lineScheduleId;

	@ManyToOne
	@JoinColumn(name = "SCHEDULE")
	private Schedule schedule;

	@ManyToOne
	@JoinColumn(name = "LINE")
	private Line line;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "scheduledLine")
	private Set<UserScheduledLine> affectedUsers;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "scheduledLine")
	private Set<Notification> notifications;
	
	public Set<UserScheduledLine> getAffectedUsers() {
		return affectedUsers;
	}
	
	public Set<Notification> getNotifications() {
		return notifications;
	}
	
	public int getLineScheduleId() {
		return lineScheduleId;
	}

	public void setLineScheduleId(int lineScheduleId) {
		this.lineScheduleId = lineScheduleId;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	@Override
	public String toString() {
		return "ScheduledLine{" +
				"lineScheduleId=" + lineScheduleId +
//				", schedule=" + schedule + TODO
//				", line=" + line + TODO
				", affectedUsers=" + affectedUsers +
				", notifications=" + notifications +
				'}';
	}
}
