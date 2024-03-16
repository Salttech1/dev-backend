package kraheja.commons.entity;

import java.io.Serializable;

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

public class MatcertlnvendoradrCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Type(type = "kraheja.commons.utils.CharType") 
	private String mcvaAdsegment ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String mcvaAdowner ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String mcvaAdtype ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String mcvaAdser ; 


}