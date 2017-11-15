package model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "COMMENT")
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COMMENT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int commentId;

	@ManyToOne
	@JoinColumn(name = "NOTIFICATION")
	private Notification notification;

	@ManyToOne
	@JoinColumn(name = "USER")
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMMENT_DATE")
	private Date date;

	@Column(name = "COMMENT_TEXT")
	private String commentText;

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getDate() {
		return Converter.dateToLocalDateTime(date);
	}

	public void setDate(LocalDateTime date) {
		this.date = Converter.localDateTimeToDate(date);
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	@Override
	public String toString() {
		return "Comment{" +
				"commentId=" + commentId +
				", notification=" + notification +
				", user=" + user + 
				", date=" + date +
				", commentText='" + commentText + '\'' +
				'}';
	}
}
