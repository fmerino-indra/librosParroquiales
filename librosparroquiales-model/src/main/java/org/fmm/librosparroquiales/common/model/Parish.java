package org.fmm.librosparroquiales.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


/**
 * The persistent class for the person database table.
 * 
 */
@Entity
public class Parish implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String name;

	@Column
	private String nif;

	@Column
	private String tfno;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="address_id", insertable = true,updatable = true, nullable = false)
	private Address address;
	
	@OneToMany(mappedBy = "parish")
	private List<ParishPriest> parishPriestList = new ArrayList<>();

	public ParishPriest getParishPriestActive() {
		return parishPriestList.stream()
				.filter(ParishPriest::isActive)
				.findFirst()
				.orElse(null);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getTfno() {
		return tfno;
	}

	public void setTfno(String tfno) {
		this.tfno = tfno;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<ParishPriest> getParishPriestList() {
		return parishPriestList;
	}

	public void setParishPriestList(List<ParishPriest> parishPriestList) {
		this.parishPriestList = parishPriestList;
	}
}