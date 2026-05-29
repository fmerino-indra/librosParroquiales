package org.fmm.librosparroquiales.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;


/**
 * The persistent class for the mobile_number database table.
 * 
 */
@Entity
@Table(name="mobile_number")
@NamedQuery(name="MobileNumber.findAll", query="SELECT m FROM MobileNumber m")
public class MobileNumber implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="mobile_number")
	private String mobileNumber;

	//bi-directional many-to-one association to Person
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn(name = "person_id", insertable = true,updatable = true, nullable = false)
	@JsonBackReference
	private Person person;

	public MobileNumber() {
	}

	public MobileNumber(String mobile) {
		this.mobileNumber = mobile;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}