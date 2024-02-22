package kraheja.enggsys.entity;


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

public class LcauthCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String lcahBldgcode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String lcahMatgroup ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String lcahPartycode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String lcahAuthnum ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String lcahAuthtype ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}