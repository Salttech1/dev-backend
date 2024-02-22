package kraheja.payroll.entity;

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

public class EmpsalaryprocessCK implements Serializable{

	private static final long serialVersionUID = 1L;

	 
	private Double espdSessionid ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String espdEmpcode ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String espdEarndedcode ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String espdSalarytype ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String espdPaymonth ; 


}