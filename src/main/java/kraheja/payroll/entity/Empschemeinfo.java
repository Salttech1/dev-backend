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
 * The persistent class for the EMPSCHEMEINFO database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPSCHEMEINFO", columnList = "eschEmpcode Asc, eschSchemecode Asc, eschEffectivefrom Asc", unique = true)
})

public class Empschemeinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpschemeinfoCK empschemeinfoCK;

	@Column(name="ESCH_APPLICABLEYN")
	private String eschApplicableyn ;

	@Column(name="ESCH_EFFECTIVEUPTO")
	private LocalDate eschEffectiveupto ;

	@Column(name="ESCH_EMPSCHEMENO")
	private String eschEmpschemeno ;

	@Column(name="ESCH_ENTRYDATE")
	private LocalDate eschEntrydate ;

	@Column(name="ESCH_IPADDRESS")
	private String eschIpaddress ;

	@Column(name="ESCH_MACHINENAME")
	private String eschMachinename ;

	@Column(name="ESCH_MODIFIEDON")
	private LocalDateTime eschModifiedon ;

	@Column(name="ESCH_MODULE")
	private String eschModule ;

	@Column(name="ESCH_REMARK")
	private String eschRemark ;

	@Column(name="ESCH_SCHEMEAMOUNT")
	private Double eschSchemeamount ;

	@Column(name="ESCH_SCHEMECENTRE")
	private String eschSchemecentre ;

	@Column(name="ESCH_SCHEMEENDDATE")
	private LocalDate eschSchemeenddate ;

	@Column(name="ESCH_SCHEMEPERCENTAGE")
	private Integer eschSchemepercentage ;

	@Column(name="ESCH_SITE")
	private String eschSite ;

	@Column(name="ESCH_USERID")
	private String eschUserid ;

}