package kraheja.purch.entity;

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

public class TempDrscrsageingCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	 
	private Double tempSesid ; 

	@Column
	 
	private Double tempSrno ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String tempPartycode ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}