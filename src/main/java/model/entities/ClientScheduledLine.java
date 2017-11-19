package model.entities;

import util.Converter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author Mohamed T. KASSAR
 *
 */

@NamedQuery(name = "getClientScheduledLineByTime", query = "select s from ClientScheduledLine s where s.beginTime <= :time and s.endTime >= :time and s.day = :day ")
@Entity
@Table(name = "CLIENT_SCHEDULED_LINE", uniqueConstraints = @UniqueConstraint(columnNames = { "CLIENT", "LINE", "BEGIN_TIME",
		"END_TIME", "DAY" }))
public class ClientScheduledLine implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum Day {
		monday, tuesday, wednesday, thursday, friday, saturday, sunday
	}

	@Id
	@Column(name = "CLIENT_SCHEDULED_LINE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int lineScheduleId;

	@ManyToOne
	@JoinColumn(name = "CLIENT")
	private Client client;

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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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
	public String toString() {
		return "{" +
				"\"lineScheduleId\":" + lineScheduleId +
				", \"line\":" + line +
				", \"beginTime\":\"" + beginTime.getHours() +':'+beginTime.getMinutes() +'\"'+
				", \"endTime\":\"" + endTime.getHours() +':'+ endTime.getMinutes() +'\"'+
				", \"day\":\"" + day +'\"'+
				'}';
	}
}
