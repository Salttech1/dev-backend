package kraheja.enggsys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the CONTRACTDEBIT database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "PK_DEBITNO", columnList = "ccdDebitno Asc", unique = true)
})

public class Contractdebit implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ContractdebitCK contractdebitCK;

	@Column(name="CCD_AMOUNT_ADJ")
	private Double ccdAmountAdj ;

	@Column(name="CCD_AUTHNUM")
	private String ccdAuthnum ;

	@Column(name="CCD_BLDGCODE")
	private String ccdBldgcode ;

	@Column(name="CCD_CERTTYPE")
	private String ccdCerttype ;

	@Column(name="CCD_CONTRACT")
	private String ccdContract ;

	@Column(name="CCD_CONTRA_CONTRACT")
	private String ccdContraContract ;

	@Column(name="CCD_DEBITTYPE")
	private String ccdDebittype ;

	@Column(name="CCD_DEBIT_AMOUNT")
	private Double ccdDebitAmount ;

	@Column(name="CCD_REMARKS")
	private String ccdRemarks ;

	@Column(name="CCD_RUNSER")
	private String ccdRunser ;

	@Column(name="CCD_SITE")
	private String ccdSite ;

	@Column(name="CCD_TODAY")
	private LocalDateTime ccdToday ;

	@Column(name="CCD_USERID")
	private String ccdUserid ;
	
	@Embeddable
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor

	public static class ContractdebitCK implements Serializable{

		private static final long serialVersionUID = 1L;

		@Type(type = "kraheja.commons.utils.CharType") 
		private String ccdDebitno ; 

	}

}