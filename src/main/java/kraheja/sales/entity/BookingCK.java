package kraheja.sales.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BookingCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String bookBldgcode;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String bookWing;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String bookFlatnum;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String bookOwnerid;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
