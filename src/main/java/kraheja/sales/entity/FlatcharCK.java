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
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FlatcharCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fchBldgcode;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fchFlatnum;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fchAccomtype;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fchChargecode;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fchWing;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}