package org.fmm.librosparroquiales.common.model;

import org.fmm.librosparroquiales.common.util.enums.BaptismRelativeEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "relative")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "relative_type", discriminatorType= DiscriminatorType.STRING)
public class Relative {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
//	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "person_data")
	private Person personData;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;
	@Column
	private String birthLocation;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "relative_type", insertable = false, updatable = false)
	private BaptismRelativeEnum relativeType;

	private String nif;

	public Relative() {
		super();
	}

	@Transient
	public String getFullName() {
		return getPersonData().getFullName();
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Person getPersonData() {
		return personData;
	}

	public void setPersonData(Person personData) {
		this.personData = personData;
	}

	public String getBirthLocation() {
		return birthLocation;
	}

	public void setBirthLocation(String birthLocation) {
		this.birthLocation = birthLocation;
	}

	public BaptismRelativeEnum getRelativeType() {
		return relativeType;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

//	private void setRelativeType(BaptismRelativeEnum relativeType) {
//		this.relativeType = relativeType;
//	}
//
}