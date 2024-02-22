// Developed By  - 	vikram.p
// Developed on - 16-12-23
// Mode  - Data Entry
// Purpose - Lunitdtls Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.entity;

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

public class LunitdtlsCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Type(type = "kraheja.commons.utils.CharType") 
	private String lessorPropcode ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String lessorUnitid ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String lessorUnitno ; 


}