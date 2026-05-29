package org.fmm.librosparroquiales.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;


/**
 * The persistent class for the email_account database table.
 * 
 */
@Entity
@Table(name="email_account")
@NamedQuery(name="EmailAccount.findAll", query="SELECT e FROM EmailAccount e")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id",
		  scope = EmailAccount.class)
public class EmailAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="email_account")
	private String emailAccount;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name = "person_id", insertable = true,updatable = true, nullable = false)
	@JsonBackReference
	private Person person;

	public EmailAccount() {
	}

	public EmailAccount(String email) {
		this.emailAccount = email;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmailAccount() {
		return this.emailAccount;
	}

	public void setEmailAccount(String emailAccount) {
		this.emailAccount = emailAccount;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}