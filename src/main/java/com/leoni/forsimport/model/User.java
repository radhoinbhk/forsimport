package com.leoni.forsimport.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.tapestry5.ValidationException;

/**
 * The Person entity.
 */
@Entity
@SuppressWarnings("serial")
public class User implements Serializable {
	private static final String DEVELOPER = "developer";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private int id;

	@Version
	@Column(nullable = false)
	private Integer version;

	@Column(length = 10, nullable = false)
	@NotNull
	@Size(max = 10)
	private String emailUser;

	@Column(length = 10, nullable = false)
	@NotNull
	@Size(max = 10)
	private String ProfilUser;

	@Temporal(TemporalType.DATE)
	@NotNull
	private String mdpUser;

	public String toString() {
		final String DIVIDER = ", ";

		StringBuilder buf = new StringBuilder();
		buf.append(this.getClass().getSimpleName() + ": ");
		buf.append("[");
		buf.append("id=" + id + DIVIDER);
		buf.append("emailUser=" + emailUser + DIVIDER);
		buf.append("typeUser=" + ProfilUser + DIVIDER);
		buf.append("mdpUser=" + mdpUser);
		buf.append("]");
		return buf.toString();
	}

	// Default constructor is required by JPA.
	public User() {
	}

	public User(String emailUser, String typeUser, String mdpUser) {
		super();
		this.emailUser = emailUser;
		this.ProfilUser = typeUser;
		this.mdpUser = mdpUser;
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

	public int getIdUser() {
		return id;
	}
	
	public void setIdUser(int id) {
		this.id = id;
	}
	
	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public String getProfilUser() {
		return ProfilUser;
	}

	public void setProfilUser(String typeUser) {
		this.ProfilUser = ProfilUser;
	}

	public String getMdpUser() {
		return mdpUser;
	}

	public void setMdpUser(String mdpUser) {
		this.mdpUser = mdpUser;
	}

	public boolean isDeveloper() {
		return DEVELOPER.equals(ProfilUser);

	}
}
