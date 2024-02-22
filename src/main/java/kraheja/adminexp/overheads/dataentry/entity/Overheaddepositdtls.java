package kraheja.adminexp.overheads.dataentry.entity;

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
 * The persistent class for the OVERHEADDEPOSITDTLS database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "OHDE1", columnList = "ohdeConnocode Asc, ohdePeriod Asc", unique = true)
})

public class Overheaddepositdtls implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OverheaddepositdtlsCK overheaddepositdtlsCK;

	@Column(name="OHDE_ADDDEDUCTION")
	private String ohdeAdddeduction ;

	@Column(name="OHDE_BILLTYPE")
	private String ohdeBilltype ;

	@Column(name="OHDE_BLDGCODE")
	private String ohdeBldgcode ;

	@Column(name="OHDE_CONNO")
	private String ohdeConno ;

	@Column(name="OHDE_COYCODE")
	private String ohdeCoycode ;

	@Column(name="OHDE_DEPOSITEAMT")
	private Double ohdeDepositeamt ;

	@Column(name="OHDE_REMARKS")
	private String ohdeRemarks ;

	@Column(name="OHDE_SITE")
	private String ohdeSite ;

	@Column(name="OHDE_TODAY")
	private LocalDateTime ohdeToday ;

	@Column(name="OHDE_TRANSER")
	private String ohdeTranser ;

	@Column(name="OHDE_USERID")
	private String ohdeUserid ;

}