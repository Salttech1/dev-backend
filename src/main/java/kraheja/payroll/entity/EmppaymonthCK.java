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

public class EmppaymonthCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Type(type = "kraheja.commons.utils.CharType") 
	private String empmEmpcode ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String empmSalarytype ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String empmPaymonth ; 


}