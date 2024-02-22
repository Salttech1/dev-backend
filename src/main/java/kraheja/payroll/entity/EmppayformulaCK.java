// Developed By  - 	kalpana.m
// Developed on - 21-08-23
// Mode  - Data Entry
// Purpose - Emppayformula Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.payroll.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class EmppayformulaCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Type(type = "kraheja.commons.utils.CharType") 
	private String eforCoy ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String eforEarndedcode ; 

	 
	private LocalDate eforEffectivefrom ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String eforEmptype ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String eforJobtype ; 


}