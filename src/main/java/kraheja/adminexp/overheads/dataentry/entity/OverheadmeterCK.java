package kraheja.adminexp.overheads.dataentry.entity;

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
public class OverheadmeterCK implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Column(name="OHME_CONNOCODE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String ohmeconnocode; 

	@Column(name="OHME_METERNO")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String ohmeMeterno ;  

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
