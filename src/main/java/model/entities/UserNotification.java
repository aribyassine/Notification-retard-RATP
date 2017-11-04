package model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import util.Converter;


/**
 * @author Mohamed T. KASSAR
 *
 */

@Entity
@Table(name = "USER_NOTIFICATION")
public class UserNotification implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "USER")
	private User user;

	@ManyToOne
	@JoinColumn(name = "NOTIFICATION")
	private Notification notification;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "USER_NOTIFICATION_DATE")
	private Date date;
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = Converter.localDateTimeToDate(date);
	}
	
	public User getUser() {
		return user;
	}
	public Notification getNotification() {
		return notification;
	}
	
	public LocalDateTime getDate() {
		return Converter.dateToLocalDateTime(date);
	}
}
