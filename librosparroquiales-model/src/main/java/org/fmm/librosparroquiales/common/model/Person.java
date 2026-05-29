package org.fmm.librosparroquiales.common.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.fmm.librosparroquiales.common.util.enums.GenderEnum;
import org.fmm.oauth2.common.model.model.AbstractPerson;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;


/**
 * The persistent class for the person database table.
 * 
 */
@Entity
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
@DiscriminatorValue("PERSON")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id",
		  scope = Person.class)
public class Person extends AbstractPerson implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column
	private LocalDate birthday;

	@Enumerated(EnumType.STRING) // Guarda "M" o "F" como texto
	@Column(nullable = false, length = 1)
	private GenderEnum gender;

	private String name;

	private String nickname;

	private String surname1;

	private String surname2;

	//bi-directional many-to-one association to EmailAccount
	@OneToMany(mappedBy="person", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<EmailAccount> emailAccounts;

	//bi-directional many-to-one association to MobileNumber
	@OneToMany(mappedBy="person", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<MobileNumber> mobileNumbers;

	@Column(name="married", nullable=false)
	private Boolean married = false;


	public Person() {
	}

	public GenderEnum getGender() {
		return this.gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSurname1() {
		return this.surname1;
	}

	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}

	public String getSurname2() {
		return this.surname2;
	}

	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}

	public EmailAccount addEmailAccount(EmailAccount emailAccount) {
		if (getEmailAccounts() == null) {
			setEmailAccounts(new HashSet<EmailAccount>());
		}
		getEmailAccounts().add(emailAccount);
		emailAccount.setPerson(this);

		return emailAccount;
	}

	public EmailAccount removeEmailAccount(EmailAccount emailAccount) {
		getEmailAccounts().remove(emailAccount);
		emailAccount.setPerson(null);

		return emailAccount;
	}

	public MobileNumber addMobileNumber(MobileNumber mobileNumber) {
		if (getMobileNumbers() == null) {
			setMobileNumbers(new HashSet<MobileNumber>());
		}
		getMobileNumbers().add(mobileNumber);
		mobileNumber.setPerson(this);

		return mobileNumber;
	}

	public MobileNumber removeMobileNumber(MobileNumber mobileNumber) {
		getMobileNumbers().remove(mobileNumber);
		mobileNumber.setPerson(null);

		return mobileNumber;
	}

	public Boolean getMarried() {
		return married;
	}

	public void setMarried(Boolean married) {
		this.married = married;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public Set<EmailAccount> getEmailAccounts() {
		return emailAccounts;
	}

	public void setEmailAccounts(Set<EmailAccount> emailAccounts) {
		this.emailAccounts = emailAccounts;
	}

	public Set<MobileNumber> getMobileNumbers() {
		return mobileNumbers;
	}

	public void setMobileNumbers(Set<MobileNumber> mobileNumbers) {
		this.mobileNumbers = mobileNumbers;
	}
}