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
 * The persistent class for the EMPSALARYPROCESS database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPSALARYPROCESS1", columnList = "espdSessionid Asc, espdEmpcode Asc, espdEarndedcode Asc, espdSalarytype Asc, espdPaymonth Asc", unique = true)
})

public class Empsalaryprocess implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpsalaryprocessCK empsalaryprocessCK;

	@Column(name="ESPD_COMPANY")
	private String espdCompany ;

	@Column(name="ESPD_COMPUTECOUNT")
	private Double espdComputecount ;

	@Column(name="ESPD_COMPUTENO")
	private Double espdComputeno ;

	@Column(name="ESPD_CURRRATE")
	private Double espdCurrrate ;

	@Column(name="ESPD_EARNDEDAMT")
	private Double espdEarndedamt ;

	@Column(name="ESPD_IPADDRESS")
	private String espdIpaddress ;

	@Column(name="ESPD_LASTMTHRATE")
	private Double espdLastmthrate ;

	@Column(name="ESPD_LASTYRRATE")
	private Double espdLastyrrate ;

	@Column(name="ESPD_MACHINENAME")
	private String espdMachinename ;

	@Column(name="ESPD_MODIFIEDON")
	private LocalDateTime espdModifiedon ;

	@Column(name="ESPD_MODULE")
	private String espdModule ;

	@Column(name="ESPD_ONHOLD")
	private Double espdOnhold ;

	@Column(name="ESPD_PAIDFROM")
	private LocalDate espdPaidfrom ;

	@Column(name="ESPD_PAIDUPTO")
	private LocalDate espdPaidupto ;

	@Column(name="ESPD_PREVMTHRATE")
	private Double espdPrevmthrate ;

	@Column(name="ESPD_RECOMPCOUNT")
	private Double espdRecompcount ;

	@Column(name="ESPD_SALREVISON")
	private String espdSalrevison ;

	@Column(name="ESPD_SCHEMEAPPLYN")
	private String espdSchemeapplyn ;

	@Column(name="ESPD_SITE")
	private String espdSite ;

	@Column(name="ESPD_USERID")
	private String espdUserid ;

}