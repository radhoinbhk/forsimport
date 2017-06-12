package com.leoni.forsimport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.tapestry5.ValidationException;

/**
 * The user entity.
 */
@Entity
@NamedQueries(
{
        @NamedQuery(name = User.ALL, query = "Select u from User u"),
        @NamedQuery(name = User.BY_USERNAME_OR_EMAIL, query = "Select u from User u where u.emailUser = :emailUser"),
        @NamedQuery(name = User.BY_CREDENTIALS, query = "Select u from User u where u.emailUser = :emailUser and u.password = :password") })
@Table(name = "users")
public class User  {

    public static final String ALL = "User.all";

    public static final String BY_USERNAME_OR_EMAIL = "User.byUserNameOrEmail";

    public static final String BY_CREDENTIALS = "User.byCredentials";

	private static final String DEVELOPER = "developer";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Integer id;

	@Column(length = 255, nullable = true)
	private String emailUser;

	@Column(length = 255, nullable = true)
	@NotNull
	@Size(max = 255)
	private String profilUser;

	private String password;

	public String toString() {
		final String DIVIDER = ", ";

		StringBuilder buf = new StringBuilder();
		buf.append(this.getClass().getSimpleName() + ": ");
		buf.append("[");
		buf.append("id=" + id + DIVIDER);
		buf.append("emailUser=" + emailUser + DIVIDER);
		buf.append("profiluser=" + profilUser + DIVIDER);
		buf.append("password=" + password);
		buf.append("]");
		return buf.toString();
	}

	// Default constructor is required by JPA.
	public User() {
	}

	public User(String emailUser, String profilUser, String password) {
		super();
		this.emailUser = emailUser;
		this.profilUser = profilUser;
		this.password = password;
	}

	// The need for an equals() method is discussed at
	// http://www.hibernate.org/109.html

	// @Override
	// public boolean equals(Object obj) {
	// return (obj == this) || (obj instanceof User) && id != null &&
	// id.equals(((User) obj).getId());
	// }

	// The need for a hashCode() method is discussed at
	// http://www.hibernate.org/109.html

	// @Override
	// public int hashCode() {
	// return id == null ? super.hashCode() : id.hashCode();
	// }

	@PrePersist
	@PreUpdate
	public void validate() throws ValidationException {

	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the emailUser
	 */
	public String getEmailUser() {
		return emailUser;
	}

	/**
	 * @param emailUser the emailUser to set
	 */
	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	/**
	 * @return the profilUser
	 */
	public String getProfilUser() {
		return profilUser;
	}

	/**
	 * @param profilUser the profilUser to set
	 */
	public void setProfilUser(String profilUser) {
		this.profilUser = profilUser;
	}

	/**
	 * @return the mdpUser
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param mdpUser the mdpUser to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDeveloper() {
		return DEVELOPER.equals(profilUser);

	}
}
