package kraheja.sales.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor

public class OutcorpCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
	private String outcBldgcode;

	@Column
	private String outcFlatnum;

	@Column
	private String outcWing;

	@Column
	private String outcFlats;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
