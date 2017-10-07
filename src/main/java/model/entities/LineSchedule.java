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
@Table(name = "LINE_SCHEDULE", uniqueConstraints = @UniqueConstraint(columnNames = { "SCHEDULE", "LINE" }))
public class LineSchedule implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LINE_SCHEDULE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int lineScheduleId;

	@ManyToOne
	@JoinColumn(name = "SCHEDULE")
	// @Column(name = "SCHEDULE")
	private Schedule schedule;

	@ManyToOne
	@JoinColumn(name = "LINE")
	// @Column(name = "LINE")
	private Line line;

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

}
