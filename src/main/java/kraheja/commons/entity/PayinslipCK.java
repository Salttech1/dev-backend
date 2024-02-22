package kraheja.commons.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayinslipCK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String pslipProprietor;
	
	private String pslipCoy;
	
	private String pslipBank;
	
	private String pslipBooknum;
	
	private String pslipLeafnum;
	
	private String pslipTranser;

	
}
