package org.fmm.librosparroquiales.common.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * The persistent class for the person database table.
 * 
 */
@Entity
public class BaptismAuthorization implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private Boolean fullAuthorization;
	
	@Column
	private Boolean fatherAuthorization;
	
	@Column
	private Boolean matherAuthorization;
	
	@Column
	private Boolean godfatherAuthorization;
	
	@Column
	private Boolean godmatherAuthorization;
	
	@Column
	private Image imageAuthorization;
	
	@Column
	private LocalDate signatureDate;
}