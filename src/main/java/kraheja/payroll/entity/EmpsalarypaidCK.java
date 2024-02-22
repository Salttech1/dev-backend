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

public class EmpsalarypaidCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String espdEmpcode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String espdEarndedcode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String espdSalarytype ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String espdPaymonth ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}