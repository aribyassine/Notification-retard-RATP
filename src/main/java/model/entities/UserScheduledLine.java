package model.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import util.Converter;

/**
 * @author Mohamed T. KASSAR
 *
 */

@NamedQuery(name = "getByTime", query = "select s from UserScheduledLine s where "
		+ "TIME_TO_SEC(time(s.beginTime)) <= TIME_TO_SEC(time(:time)) "
		+ "and TIME_TO_SEC(time(s.endTime)) >= TIME_TO_SEC(time(:time)) " + "and s.day = :day ")

@Entity
@Table(name = "USER_SCHEDULED_LINE", uniqueConstraints = @UniqueConstraint(columnNames = { "USER", "LINE", "BEGIN_TIME",
		"END_TIME", "DAY" }))
public class UserScheduledLine implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum Day {
		monday, tuesday, wednesday, thursday, friday, saturday, sunday
	}

	@Id
	@Column(name = "USER_SCHEDULED_LINE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int lineScheduleId;

	@ManyToOne
	@JoinColumn(name = "USER")
	private User user;

	@ManyToOne
	@JoinColumn(name = "LINE")
	private Line line;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BEGIN_TIME")
	private Date beginTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME")
	private Date endTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "DAY")
	private Day day;

	public void setBeginTime(LocalTime beginTime) {
		this.beginTime = Converter.localTimeToDate(beginTime);
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = Converter.localTimeToDate(endTime);
	}

	public LocalTime getBeginTime() {
		return Converter.dateToLocalTime(beginTime);
	}

	public LocalTime getEndTime() {
		return Converter.dateToLocalTime(endTime);
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return lineScheduleId;
	}

	public void setId(int lineScheduleId) {
		this.lineScheduleId = lineScheduleId;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	@Override
	public String toString() {// TODO
		return "ScheduledLine{" + "lineScheduleId=" + lineScheduleId +
		// ", schedule=" + schedule + TODO
		// ", line=" + line + TODO
				'}';
	}
}
