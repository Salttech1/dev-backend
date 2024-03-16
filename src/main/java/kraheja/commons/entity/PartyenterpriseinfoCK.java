package kraheja.commons.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import java.time.LocalDate;
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

public class PartyenterpriseinfoCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Type(type = "kraheja.commons.utils.CharType") 
	private String peiPartycode ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String peiPartytype ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String peiEntcode ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String peiEnttype ; 

	 
	private LocalDate peiEnddate ; 


}