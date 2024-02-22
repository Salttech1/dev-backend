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
 * The persistent class for the EMPSALARYPAID database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPSALARYPAID1", columnList = "espdEmpcode Asc, espdEarndedcode Asc, espdSalarytype Asc, espdPaymonth Asc", unique = true)
})

public class Empsalarypaid implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpsalarypaidCK empsalarypaidCK;

	@Column(name="ESPD_EARNDEDAMT")
	private Double espdEarndedamt ;

	@Column(name="ESPD_IPADDRESS")
	private String espdIpaddress ;

	@Column(name="ESPD_MACHINENAME")
	private String espdMachinename ;

	@Column(name="ESPD_MODIFIEDON")
	private LocalDateTime espdModifiedon ;

	@Column(name="ESPD_MODULE")
	private String espdModule ;

	@Column(name="ESPD_PAIDFROM")
	private LocalDate espdPaidfrom ;

	@Column(name="ESPD_PAIDUPTO")
	private LocalDate espdPaidupto ;

	@Column(name="ESPD_SITE")
	private String espdSite ;

	@Column(name="ESPD_USERID")
	private String espdUserid ;

}