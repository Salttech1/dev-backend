package kraheja.payroll.entity;

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

public class EmploaninfoCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Type(type = "kraheja.commons.utils.CharType") 
	private String eloanEmpcode ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String eloanLoancode ; 

	 
	private LocalDate eloanIssuedate ; 


}