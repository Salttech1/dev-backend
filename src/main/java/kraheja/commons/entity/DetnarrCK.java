// Developed By  - 	kalpana.m
// Developed on - 04-08-23
// Mode  - Data Entry
// Purpose - Detnarr Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.commons.entity;

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

public class DetnarrCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String detCoy ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String detTranser ; 

	@Column
	private Integer detBunum ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}