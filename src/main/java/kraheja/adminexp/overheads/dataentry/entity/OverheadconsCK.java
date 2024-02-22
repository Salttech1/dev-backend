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
public class OverheadconsCK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String ohdhConsumerno ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String ohdhConnocode ;  

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
