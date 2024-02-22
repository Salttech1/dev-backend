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

public class FlatsCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String flatBldgcode ; 

	@Column
	//@Type(type = "kraheja.commons.utils.CharType") NS, Kevin 16.02.2023 as per advised from kevin while doing update for already existed record that has been retrieved.
	@Type(type = "kraheja.commons.utils.CharType")
	private String flatWing ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String flatFlatnum ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}