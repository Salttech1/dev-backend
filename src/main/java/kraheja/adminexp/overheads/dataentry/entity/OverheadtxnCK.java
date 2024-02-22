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
public class OverheadtxnCK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String ohddConnocode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String ohddBillperiod ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String ohddSupplementarybill ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
