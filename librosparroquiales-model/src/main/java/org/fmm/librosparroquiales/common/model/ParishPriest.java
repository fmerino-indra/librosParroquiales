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


/**
 * The persistent class for the person database table.
 * 
 */
@Entity
public class ParishPriest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="person_data")
	private Person personData;
	
	@ManyToOne
	@JoinColumn(name = "parish_id", insertable = true, updatable = true, nullable = false)
	//@JsonBackReference
	private Parish parish;

	@Column
	private String nif;
	@Column
	private Boolean active;
	@Column
	private LocalDate initDate = LocalDate.now();
	@Column
	private LocalDate endDate;
	
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
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
	public LocalDate getInitDate() {
		return initDate;
	}
	public void setInitDate(LocalDate initDate) {
		this.initDate = initDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public Parish getParish() {
		return parish;
	}
	public void setParish(Parish parish) {
		this.parish = parish;
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
	
	public void enable() {
		this.setActive(true);
		this.setEndDate(null);
		if (this.getInitDate() == null)
			this.setInitDate(LocalDate.now());
	}
	
	public void disable() {
		this.setActive(false);
		this.setEndDate(LocalDate.now());
	}
}