package kraheja.payroll.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDate;
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
 * The persistent class for the EARNDEDMASTER database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EARNDEDMASTER1", columnList = "edhmEarndedcode Asc", unique = true)
})

public class Earndedmaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EarndedmasterCK earndedmasterCK;

	@Column(name="EDHM_ACMAJOR")
	private String edhmAcmajor ;

	@Column(name="EDHM_ACMAJOR1")
	private String edhmAcmajor1 ;

	@Column(name="EDHM_ARREARSYN")
	private String edhmArrearsyn ;

	@Column(name="EDHM_COMPUTENO")
	private Integer edhmComputeno ;

	@Column(name="EDHM_COMPUTETYPE")
	private String edhmComputetype ;

	@Column(name="EDHM_DEFAULTSALTYPE")
	private String edhmDefaultsaltype ;

	@Column(name="EDHM_DISPLAYNAME")
	private String edhmDisplayname ;

	@Column(name="EDHM_DRCR")
	private String edhmDrcr ;

	@Column(name="EDHM_EARNDEDNAME")
	private String edhmEarndedname ;

	@Column(name="EDHM_EARNDEDTYPE")
	private String edhmEarndedtype ;

	@Column(name="EDHM_IPADDRESS")
	private String edhmIpaddress ;

	@Column(name="EDHM_LOANYN")
	private String edhmLoanyn ;

	@Column(name="EDHM_MACHINENAME")
	private String edhmMachinename ;

	@Column(name="EDHM_MODIFIEDON")
	private LocalDateTime edhmModifiedon ;

	@Column(name="EDHM_MODULE")
	private String edhmModule ;

	@Column(name="EDHM_POSTGROUP")
	private String edhmPostgroup ;

	@Column(name="EDHM_PRINTYN")
	private String edhmPrintyn ;

	@Column(name="EDHM_SALSLIPPRINTYN")
	private String edhmSalslipprintyn ;

	@Column(name="EDHM_SCHEMENOAUTOYN")
	private String edhmSchemenoautoyn ;

	@Column(name="EDHM_SCHEMEYN")
	private String edhmSchemeyn ;

	@Column(name="EDHM_SETTLEPRINTYN")
	private String edhmSettleprintyn ;

	@Column(name="EDHM_SITE")
	private String edhmSite ;

	@Column(name="EDHM_USERID")
	private String edhmUserid ;

}