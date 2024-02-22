package kraheja.fd.deposit.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
 * The persistent class for the FDDDDE08 database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "DEP8", columnList = "depSesid ASC, depCoy ASC, depDepositor ASC", unique = true),
})
public class Fdddde08 implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Fdddde08CK fdddde08CK;
	
	@Column(name="DEP_ADLINE1")
	private String depAdline1;

	@Column(name="DEP_ADLINE2")
	private String depAdline2;

	@Column(name="DEP_ADLINE3")
	private String depAdline3;

	@Column(name="DEP_ADLINE4")
	private String depAdline4;

	@Column(name="DEP_AMTINWORDS")
	private String depAmtinwords;

	@Column(name="DEP_CHQAMT")
	private Double depChqamt;

	@Column(name="DEP_CHQDATE")
	private LocalDate depChqdate;

	@Column(name="DEP_CHQNUM")
	private String depChqnum;

	@Column(name="DEP_DEPNAME")
	private String depDepname;

	@Column(name="DEP_INTEREST")
	private Double depInterest;

	@Column(name="DEP_INTPAIDYTD")
	private Double depIntpaidytd;

	@Column(name="DEP_PARTYNAME")
	private String depPartyname;

	@Column(name="DEP_SITE")
	private String depSite;

	@Column(name="DEP_TAXPAIDYTD")
	private Double depTaxpaidytd;

	@Column(name="DEP_TDS")
	private Double depTds;

	@Column(name="DEP_TODAY")
	private LocalDateTime depToday;

	@Column(name="DEP_USERID")
	private String depUserid;

	@Column(name="RECPT_YN")
	private String recptYn;

}