
// Developed By  - 	vikram.p
// Developed on - 14-03-24
// Mode  - Data Entry
// Purpose - Temp_Certprint Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.enggsys.entity;

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

public class TempCertprintCK implements Serializable{

	private static final long serialVersionUID = 1L;

	private String tmpCertnum ; 

	private String tmpType ; 

	private String tmpCode ; 

	 
	private Integer tmpSessionid ; 


}