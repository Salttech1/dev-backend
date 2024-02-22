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
 * The persistent class for the EMPSALARYPACKAGE database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPSALARYPACKAGE1", columnList = "espkEmpcode Asc, espkEarndedcode Asc, espkEffectivefrom Asc", unique = true)
})

public class Empsalarypackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpsalarypackageCK empsalarypackageCK;

	@Column(name="ESPK_EARNDEDRATE")
	private Double espkEarndedrate ;

	@Column(name="ESPK_EFFECTIVEUPTO")
	private LocalDate espkEffectiveupto ;

	@Column(name="ESPK_IPADDRESS")
	private String espkIpaddress ;

	@Column(name="ESPK_MACHINENAME")
	private String espkMachinename ;

	@Column(name="ESPK_MODIFIEDON")
	private LocalDateTime espkModifiedon ;

	@Column(name="ESPK_MODULE")
	private String espkModule ;

	@Column(name="ESPK_PAYCYCLE")
	private String espkPaycycle ;

	@Column(name="ESPK_RATECYCLE")
	private String espkRatecycle ;

	@Column(name="ESPK_SITE")
	private String espkSite ;

	@Column(name="ESPK_USERID")
	private String espkUserid ;

}