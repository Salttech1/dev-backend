package kraheja.sales.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FlatpayCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fpayBldgcode;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fpayFlatnum;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fpayOwnerid;
	
	@Column
	private LocalDate fpayDuedate;
	

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fpayNarrative;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}