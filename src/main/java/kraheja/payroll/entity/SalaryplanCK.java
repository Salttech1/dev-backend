// Developed By  - 	kalpana.m
// Developed on - 17-02-24
// Mode  - Data Entry
// Purpose - Salaryplan Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.payroll.entity;

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

public class SalaryplanCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String splanSalarytype ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String splanCoy ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String splanPaymonth ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}