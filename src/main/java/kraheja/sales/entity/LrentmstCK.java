// Developed By  - 	vikram.p
// Developed on - 03-01-24
// Mode  - Data Entry
// Purpose - Lrentmst Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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


public class LrentmstCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String rentmUnitid ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String rentmUnitgroup ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String rentmType ; 

	@Column
	 
	private LocalDate rentmTo ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String rentmPropcode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String rentmUnitno ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}