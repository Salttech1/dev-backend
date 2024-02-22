package kraheja.adminexp.billing.dataentry.entity;

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

public class IntercoybillheaderCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Type(type = "kraheja.commons.utils.CharType") 
	private String icbehGroupinvoiceno ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String icbehInvoiceno ; 


}
