package org.fmm.librosparroquiales.common.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.fmm.librosparroquiales.common.model.baptism.Father;
import org.fmm.librosparroquiales.common.model.baptism.GrandFather;
import org.fmm.librosparroquiales.common.model.baptism.GrandMother;
import org.fmm.librosparroquiales.common.model.baptism.Mother;
import org.hibernate.annotations.ColumnDefault;

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
public class BaptismRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne //(cascade = CascadeType.ALL)
	@JoinColumn(name="candidate")
	private BaptismalCandidate candidate;
	
	@Column
	private LocalDate appointment;
	
	@Column
	private LocalDate requestDate;
	
	@Column
	private boolean celebrated;

	@Column
	private Integer book;
	
	@Column
	private Integer sheet;
	
	@Column
	private Integer number;
	
	@ManyToOne
	@JoinColumn(name="father_id")
	private Father father;
	
	@ManyToOne
	@JoinColumn(name="mother_id")
	private Mother mother;
	
	// Father GrandParents
	@ManyToOne
	@JoinColumn(name="father_grandfather_id")
	private GrandFather fatherGrandFather;
	
	@ManyToOne
	@JoinColumn(name="father_grandmother_id")
	private GrandMother fatherGrandMother;
	
	// Mother GrandParents
	@ManyToOne
	@JoinColumn(name="mother_grandfather_id")
	private GrandFather motherGrandFather;
	
	@ManyToOne
	@JoinColumn(name="mother_grandmother_id")
	private GrandMother motherGrandMother;
	
	@ManyToOne
	@JoinColumn(name="godfather_id")
	private Person godFather;
	
	@ManyToOne
	@JoinColumn(name="godmother_id")
	private Person godMother;

	@ManyToOne
	@JoinColumn(name="minister_id")
	private Person minister;
	
	@Column
	// Es de hibernate
	@ColumnDefault("true")
	private boolean commonAddress;
	
	public BaptismRequest() {
		super();
		this.candidate = new BaptismalCandidate();
		this.requestDate=LocalDate.now();
	}

	public BaptismRequest(BaptismalCandidate candidate) {
		super();
		this.candidate = candidate;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BaptismalCandidate getCandidate() {
		return candidate;
	}
	public void setCandidate(BaptismalCandidate candidate) {
		this.candidate = candidate;
	}
	public boolean isCelebrated() {
		return celebrated;
	}
	public void setCelebrated(boolean celebrated) {
		this.celebrated = celebrated;
	}
	public LocalDate getAppointment() {
		return appointment;
	}
	public void setAppointment(LocalDate appointment) {
		this.appointment = appointment;
	}
	public Person getGodFather() {
		return godFather;
	}
	public void setGodFather(Person godFather) {
		this.godFather = godFather;
	}
	public Person getGodMother() {
		return godMother;
	}
	public void setGodMother(Person godMother) {
		this.godMother = godMother;
	}

	public Father getFather() {
		return father;
	}

	public void setFather(Father father) {
		this.father = father;
	}

	public Mother getMother() {
		return mother;
	}

	public void setMother(Mother mother) {
		this.mother = mother;
	}

	public GrandFather getFatherGrandFather() {
		return fatherGrandFather;
	}

	public void setFatherGrandFather(GrandFather fatherGrandFather) {
		this.fatherGrandFather = fatherGrandFather;
	}

	public GrandMother getFatherGrandMother() {
		return fatherGrandMother;
	}

	public void setFatherGrandMother(GrandMother fatherGrandMother) {
		this.fatherGrandMother = fatherGrandMother;
	}

	public GrandFather getMotherGrandFather() {
		return motherGrandFather;
	}

	public void setMotherGrandFather(GrandFather motherGrandFather) {
		this.motherGrandFather = motherGrandFather;
	}

	public GrandMother getMotherGrandMother() {
		return motherGrandMother;
	}

	public void setMotherGrandMother(GrandMother motherGrandMother) {
		this.motherGrandMother = motherGrandMother;
	}
	
	public String getBaptizingFullName() {
		return String.format("%s %s, %s", 
				this.getCandidate().getPersonData().getSurname1(), 
				this.getCandidate().getPersonData().getSurname2(), 
				this.getCandidate().getPersonData().getName());
	}
	
	public String getParentNames() {
		return String.format("%s - %s", this.father.getPersonData().getName(), this.mother.getPersonData().getName());
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public Person getMinister() {
		return minister;
	}

	public void setMinister(Person minister) {
		this.minister = minister;
	}

	public boolean isCommonAddress() {
		return commonAddress;
	}

	public void setCommonAddress(boolean commonAddress) {
		this.commonAddress = commonAddress;
	}

	public Integer getBook() {
		return book;
	}

	public void setBook(Integer book) {
		this.book = book;
	}

	public Integer getSheet() {
		return sheet;
	}

	public void setSheet(Integer sheet) {
		this.sheet = sheet;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}