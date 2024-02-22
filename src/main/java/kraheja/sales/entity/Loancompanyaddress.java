package kraheja.sales.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the LOANCOMPANYADDRESS database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(indexes = {
		@Index(name = "LOANCOMPANYADDRESS_X", columnList = "lcaLoancoycode ASC, lcaLoanbranchcode ASC", unique = true)
		
})
public class Loancompanyaddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LoancompanyaddressCK loancompanyaddressCK;

	@Column(name="LCA_ADLINE1")
	private String lcaAdline1;

	@Column(name="LCA_ADLINE2")
	private String lcaAdline2;

	@Column(name="LCA_ADLINE3")
	private String lcaAdline3;

	@Column(name="LCA_ADLINE4")
	private String lcaAdline4;

	@Column(name="LCA_ADLINE5")
	private String lcaAdline5;

	@Column(name="LCA_CITY")
	private String lcaCity;

	@Column(name="LCA_COUNTRY")
	private String lcaCountry;

	@Column(name="LCA_EMAIL")
	private String lcaEmail;

	@Column(name="LCA_FAX")
	private String lcaFax;

	@Column(name="LCA_ORIGSITE")
	private String lcaOrigsite;

	@Column(name="LCA_PHONEMOBILE")
	private String lcaPhonemobile;

	@Column(name="LCA_PHONEOFF")
	private String lcaPhoneoff;

	@Column(name="LCA_PHONERES")
	private String lcaPhoneres;

	@Column(name="LCA_PINCODE")
	private String lcaPincode;

	@Column(name="LCA_SITE")
	private String lcaSite;

	@Column(name="LCA_STATE")
	private String lcaState;

	@Column(name="LCA_TODAY")
	private LocalDateTime lcaToday;

	@Column(name="LCA_TOWNSHIP")
	private String lcaTownship;

	@Column(name="LCA_USERID")
	private String lcaUserid;

}