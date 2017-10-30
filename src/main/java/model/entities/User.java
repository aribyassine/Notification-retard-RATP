package model.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Mohamed T. KASSAR
 *
 */

@Entity
@Table(name = "USER")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "E_MAIL", unique = true)
	private String email;

	@Column(name = "PHONE_NUMBER", unique = true)
	private String phoneNumber;

	@Column(name = "PASSWORD")
	private String password;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<UserScheduledLine> affectedScheduledLine;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<Comment> comments;

	public Set<Comment> getComments() {
		return comments;
	}

	public Set<UserScheduledLine> getAffectedScheduledLine() {
		return affectedScheduledLine;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return userName.equals(((User) obj).getUserName());
		}
		return false;
	}

	@Override
	public String toString() {
		return "User{" + "userName='" + userName + '\'' + ", email='" + email + '\'' + ", phoneNumber='" + phoneNumber
				+ '\'' + ", affectedScheduledLine=" + affectedScheduledLine + ", comments=" + comments + '}';
	}
}
