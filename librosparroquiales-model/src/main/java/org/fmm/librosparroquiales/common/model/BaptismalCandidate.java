package org.fmm.librosparroquiales.common.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;


/**
 * The persistent class for the person database table.
 * 
 */
@Entity
public class BaptismalCandidate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="person_data")
	private Person personData;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="address_id")
	private Address address;
	
	@Column
	private Boolean active;
	@Column
	private String birthLocation;
	
	
	public BaptismalCandidate() {
		super();
		this.personData = new Person();
		this.address = new Address();
	}

	
	public BaptismalCandidate(Person personData, Address address) {
		super();
		this.personData = personData;
		this.address = address;
	}
	
	public boolean isActive() {
		return active;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Person getPersonData() {
		return personData;
	}
	public void setPersonData(Person personData) {
		this.personData = personData;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getBirthLocation() {
		return birthLocation;
	}
	public void setBirthLocation(String birthLocation) {
		this.birthLocation = birthLocation;
	}

	@Transient
	public LocalDate getBirthday() {
		return personData!=null?personData.getBirthday():null;
	}

}