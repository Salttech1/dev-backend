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
 * The persistent class for the EMPPAYMONTH database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPPAYMONTH", columnList = "empmEmpcode Asc, empmSalarytype Asc, empmPaymonth Asc", unique = true)
})

public class Emppaymonth implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmppaymonthCK emppaymonthCK;

	@Column(name="EMPM_ACTRANSER")
	private String empmActranser ;

	@Column(name="EMPM_ACVOUNUM")
	private String empmAcvounum ;

	@Column(name="EMPM_DAYSABSENT")
	private Double empmDaysabsent ;

	@Column(name="EMPM_DAYSADJLASTMTH")
	private Double empmDaysadjlastmth ;

	@Column(name="EMPM_DAYSADJPRVLASTMTH")
	private Double empmDaysadjprvlastmth ;

	@Column(name="EMPM_DAYSARREARS")
	private Double empmDaysarrears ;

	@Column(name="EMPM_DAYSENCASHED")
	private Double empmDaysencashed ;

	@Column(name="EMPM_DAYSNHPAY")
	private Double empmDaysnhpay ;

	@Column(name="EMPM_DAYSPAID")
	private Double empmDayspaid ;

	@Column(name="EMPM_DAYSUNIONFUND")
	private Double empmDaysunionfund ;

	@Column(name="EMPM_INSTRUMENTBANK")
	private String empmInstrumentbank ;

	@Column(name="EMPM_INSTRUMENTDATE")
	private LocalDate empmInstrumentdate ;

	@Column(name="EMPM_INSTRUMENTNO")
	private String empmInstrumentno ;

	@Column(name="EMPM_INSTRUMENTTYPE")
	private String empmInstrumenttype ;

	@Column(name="EMPM_IPADDRESS")
	private String empmIpaddress ;

	@Column(name="EMPM_ITDECLREVNO")
	private Double empmItdeclrevno ;

	@Column(name="EMPM_MACHINENAME")
	private String empmMachinename ;

	@Column(name="EMPM_MODIFIEDON")
	private LocalDateTime empmModifiedon ;

	@Column(name="EMPM_MODULE")
	private String empmModule ;

	@Column(name="EMPM_OTHOURS")
	private Double empmOthours ;

	@Column(name="EMPM_OTHRSLASTMTH")
	private Double empmOthrslastmth ;

	@Column(name="EMPM_OTHRSPRVLASTMTH")
	private Double empmOthrsprvlastmth ;

	@Column(name="EMPM_PAIDFROM")
	private LocalDate empmPaidfrom ;

	@Column(name="EMPM_PAIDUPTO")
	private LocalDate empmPaidupto ;

	@Column(name="EMPM_PAYDATE")
	private LocalDate empmPaydate ;

	@Column(name="EMPM_PAYSTATUS")
	private String empmPaystatus ;

	@Column(name="EMPM_REG_SETTLE")
	private String empmReg_Settle ;

	@Column(name="EMPM_REMARK")
	private String empmRemark ;

	@Column(name="EMPM_SITE")
	private String empmSite ;

	@Column(name="EMPM_USERID")
	private String empmUserid ;

}