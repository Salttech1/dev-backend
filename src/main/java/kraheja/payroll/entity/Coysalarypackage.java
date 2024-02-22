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
 * The persistent class for the COYSALARYPACKAGE database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "COYSALARYPACKAGE1", columnList = "cspkCoy Asc, cspkEarndedcode Asc, cspkEffectivefrom Asc", unique = true)
})

public class Coysalarypackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CoysalarypackageCK coysalarypackageCK;

	@Column(name="CSPK_EFFECTIVEUPTO")
	private LocalDate cspkEffectiveupto ;

	@Column(name="CSPK_MACHINENAME")
	private String cspkMachinename ;

	@Column(name="CSPK_MODIFIEDON")
	private LocalDateTime cspkModifiedon ;

	@Column(name="CSPK_MODULE")
	private String cspkModule ;

	@Column(name="CSPK_ORDERINEXCELRP")
	private Integer cspkOrderinexcelrp ;

	@Column(name="CSPK_PAYCYCLE")
	private String cspkPaycycle ;

	@Column(name="CSPK_RATECYCLE")
	private String cspkRatecycle ;

	@Column(name="CSPK_ROUNDUPTODEC")
	private Integer cspkRounduptodec ;

	@Column(name="CSPK_SCHEMENO")
	private String cspkSchemeno ;

	@Column(name="CSPK_SHOWINSALPACK")
	private String cspkShowinsalpack ;

	@Column(name="CSPK_SITE")
	private String cspkSite ;

	@Column(name="CSPK_USERID")
	private String cspkUserid ;

}