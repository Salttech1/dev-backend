package kraheja.payroll.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDate;
import javax.persistence.Column;
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
 * The persistent class for the COYMTHSALTYPES database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "COYMTHSALTYPES1", columnList = "cmstCoy Asc, cmstSalarytype Asc", unique = true)
})

public class Coymthsaltypes implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CoymthsaltypesCK coymthsaltypesCK;

	@Column(name="CMST_ACTIVEYN")
	private String cmstActiveyn ;

	@Column(name="CMST_BANKADVNO")
	private Double cmstBankadvno ;

	@Column(name="CMST_BONUSPAIDYN")
	private String cmstBonuspaidyn ;

	@Column(name="CMST_BONUSPERIODYN")
	private String cmstBonusperiodyn ;

	@Column(name="CMST_CURRBONUSDT")
	private LocalDate cmstCurrbonusdt ;

	@Column(name="CMST_EARNDEDCODE")
	private String cmstEarndedcode ;

	@Column(name="CMST_INITIALISEDON")
	private LocalDateTime cmstInitialisedon ;

	@Column(name="CMST_IPADDRESS")
	private String cmstIpaddress ;

	@Column(name="CMST_ITDEFERMTH")
	private String cmstItdefermth ;

	@Column(name="CMST_ITNEXTMTH")
	private String cmstItnextmth ;

	@Column(name="CMST_ITREVISIONNO")
	private Double cmstItrevisionno ;

	@Column(name="CMST_LASTBONUSDT")
	private LocalDate cmstLastbonusdt ;

	@Column(name="CMST_MACHINENAME")
	private String cmstMachinename ;

	@Column(name="CMST_MODIFIEDON")
	private LocalDateTime cmstModifiedon ;

	@Column(name="CMST_MODULE")
	private String cmstModule ;

	@Column(name="CMST_MTHSALREVNO")
	private Double cmstMthsalrevno ;

	@Column(name="CMST_SITE")
	private String cmstSite ;

	@Column(name="CMST_USERID")
	private String cmstUserid ;

	@Column(name="CMST_YRENDMONTH")
	private String cmstYrendmonth ;

	@Column(name="CMST_YRSALREVNO")
	private Double cmstYrsalrevno ;

	@Column(name="CMST_YRSTARTMONTH")
	private String cmstYrstartmonth ;
	
	@Column(name="CMST_PAYMONTH")
	private String cmstPaymonth ; 


}