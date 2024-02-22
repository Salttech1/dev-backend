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
@AllArgsConstructor
@NoArgsConstructor

public class OutrateCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String outrBldgcode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String outrFlatnum ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String outrWing ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String outrStartdate ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}