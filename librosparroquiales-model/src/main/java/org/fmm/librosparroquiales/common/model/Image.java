package org.fmm.librosparroquiales.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;


/**
 * The persistent class for the person_image database table.
 * 
 */
@Entity
@Table(name="image")
@NamedQuery(name="Image.findAll", query="SELECT i FROM Image i")
public class Image implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

//	@Lob
	@Column(nullable = false, name = "photo_small", columnDefinition="bytea")
	@JsonIgnore
	private byte[] smallPhoto;

//	@Lob
	@Column(nullable = false, name = "photo_tiny", columnDefinition="bytea")
	private byte[] tinyPhoto;

	@Column(name="mime_type")
	private String mimeType;

//	@Transient
//	private String imageBase64;
	
	public Image() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getTinyPhoto() {
		return this.tinyPhoto;
	}

	public void setTinyPhoto(byte[] photo) {
		this.tinyPhoto = photo;
//		this.imageBase64 = convertToBase64();
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getSmallPhoto() {
		return smallPhoto;
	}

	public void setSmallPhoto(byte[] smallPhoto) {
		this.smallPhoto = smallPhoto;
	}

//	public String getImageBase64() {
//		if (imageBase64 == null)
//			imageBase64 = convertToBase64();
//		return imageBase64;
//	}
//
//	public void setImageBase64(String imageBase64) {
//		this.imageBase64 = imageBase64;
//		this.photo = convertToBinary();
//	}
	
//	private String convertToBase64() {
//		if (photo != null) 
//			return Base64.getEncoder().encodeToString(photo);
//		else
//			return null;
//	}
//	
//	private byte[] convertToBinary() {
//		if (imageBase64 != null)
//			return Base64.getDecoder().decode(imageBase64);
//		else
//			return null;
//		
//	}
}