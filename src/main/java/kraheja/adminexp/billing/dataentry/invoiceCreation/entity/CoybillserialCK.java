package kraheja.adminexp.billing.dataentry.invoiceCreation.entity;

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

public class CoybillserialCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Type(type = "kraheja.commons.utils.CharType") 
	private String cbsCoycode ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String cbsChargecode ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String cbsBilltype ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String cbsSitename ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String cbsYear ; 


}