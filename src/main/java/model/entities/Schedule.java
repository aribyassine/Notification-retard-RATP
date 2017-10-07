package model.entities;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Mohamed T. KASSAR
 *
 */

@Entity
@Table(name = "SCHEDULE")
public class Schedule implements Serializable {

	public static enum Day {
		sunday, monday, theursday, wendesday, tuesday, friday, saturday
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SCHEDULE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int scheduleId;

	@Column(name = "HOUR")
	private int hour;

	@Column(name = "MINUTE")
	private int minute;

	@Column(name = "DAY")
	private Day day;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "schedule")
	private List<LineSchedule> ScheduledLines;


	public List<Line> getScheduledLines() {
		return ScheduledLines.stream().map(ls -> ls.getLine()).collect(Collectors.toList());
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int id) {
		this.scheduleId = id;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	@Enumerated(EnumType.STRING)
	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + scheduleId + ", hour=" + hour + ", minute=" + minute + ", day=" + day + "]";
	}

}
