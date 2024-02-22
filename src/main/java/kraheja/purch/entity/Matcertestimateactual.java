package kraheja.purch.entity;

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
 * The persistent class for the MATCERTESTIMATEACTUAL database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "MATCERTESTIMATEACTUAL", columnList = "clseProjcode Asc, clseMatcerttype Asc, clseMatcertcode Asc, clseGroupcode Asc, clseSubgroupcode Asc, clsePartytype Asc, clsePartycode Asc", unique = true)
})

public class Matcertestimateactual implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MatcertestimateactualCK matcertestimateactualCK;

	@Column(name="CLSE_ACTIONBY")
	private String clseActionby ;

	@Column(name="CLSE_ACTWORKDONE")
	private Double clseActworkdone ;

	@Column(name="CLSE_CONTRACTVAL")
	private Double clseContractval ;

	@Column(name="CLSE_CONTVALADJ")
	private Double clseContvaladj ;

	@Column(name="CLSE_DELIVERYDATE")
	private LocalDate clseDeliverydate ;

	@Column(name="CLSE_DELIVERYWEEKS")
	private String clseDeliveryweeks ;

	@Column(name="CLSE_ESTIMATEAMT")
	private Double clseEstimateamt ;

	@Column(name="CLSE_LOGICNOTENUM")
	private String clseLogicnotenum ;

	@Column(name="CLSE_ORDERDATE")
	private LocalDate clseOrderdate ;

	@Column(name="CLSE_OTHDEBITS")
	private Double clseOthdebits ;

	@Column(name="CLSE_QUOTEDATE")
	private LocalDate clseQuotedate ;

	@Column(name="CLSE_SITE")
	private String clseSite ;

	@Column(name="CLSE_TENDERDATE")
	private LocalDate clseTenderdate ;

	@Column(name="CLSE_TODAY")
	private LocalDateTime clseToday ;

	@Column(name="CLSE_USERID")
	private String clseUserid ;

}