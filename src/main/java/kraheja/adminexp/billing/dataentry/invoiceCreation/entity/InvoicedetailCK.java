package kraheja.adminexp.billing.dataentry.invoiceCreation.entity;

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

public class InvoicedetailCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String invdTrtype ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String invdInvoiceno ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String invdCode ; 

	@Column
	 
	private Integer invdSrno ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}