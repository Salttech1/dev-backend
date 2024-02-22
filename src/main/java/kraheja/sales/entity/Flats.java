package kraheja.sales.entity;

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
 * The persistent class for the FLATS database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "FLAT1", columnList = "flatBldgcode Asc, flatWing Asc, flatFlatnum Asc", unique = true)
})

public class Flats implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FlatsCK flatsCK;

	@Column(name="FLAT_ACCOMTYPE")
	private String flatAccomtype ;

	@Column(name="FLAT_AGPRICE")
	private Double flatAgprice ;

	@Column(name="FLAT_AMTOS")
	private Double flatAmtos ;

	@Column(name="FLAT_AMTREC")
	private Double flatAmtrec ;

	@Column(name="FLAT_BAMENAREA")
	private Double flatBamenarea ;

	@Column(name="FLAT_BPARKAREA")
	private Double flatBparkarea ;

	@Column(name="FLAT_BROKER")
	private String flatBroker ;

	@Column(name="FLAT_BTERAAREA")
	private Double flatBteraarea ;

	@Column(name="FLAT_BUNITAREA")
	private Double flatBunitarea ;

	@Column(name="FLAT_CAMENAREA")
	private Double flatCamenarea ;

	@Column(name="FLAT_CONFIG")
	private String flatConfig ;

	@Column(name="FLAT_CONTRACTON")
	private LocalDateTime flatContracton ;

	@Column(name="FLAT_COY")
	private String flatCoy ;

	@Column(name="FLAT_CPARKAREA")
	private Double flatCparkarea ;

	@Column(name="FLAT_CTERAAREA")
	private Double flatCteraarea ;

	@Column(name="FLAT_CUNITAREA")
	private Double flatCunitarea ;

	@Column(name="FLAT_CURERA")
	private Double flatCurera ;

	@Column(name="FLAT_CUSTID")
	private String flatCustid ;

	@Column(name="FLAT_CUSTTYPE")
	private String flatCusttype ;

	@Column(name="FLAT_DISCOUNT")
	private Double flatDiscount ;

	@Column(name="FLAT_ENCLBALCRERA")
	private Double flatEnclbalcrera ;

	@Column(name="FLAT_FLATPARK")
	private String flatFlatpark ;

	@Column(name="FLAT_FLOOR")
	private String flatFloor ;

	@Column(name="FLAT_HO2OWNER")
	private LocalDateTime flatHo2owner ;

	@Column(name="FLAT_INTRATE")
	private Double flatIntrate ;

	@Column(name="FLAT_LEASEDTO")
	private String flatLeasedto ;

	@Column(name="FLAT_LEASEREF")
	private String flatLeaseref ;

	@Column(name="FLAT_LOANAMT")
	private Double flatLoanamt ;

	@Column(name="FLAT_LOANBRANCH")
	private String flatLoanbranch ;

	@Column(name="FLAT_LOANCLOSEDATE")
	private LocalDateTime flatLoanclosedate ;

	@Column(name="FLAT_LOANCO")
	private String flatLoanco ;

	@Column(name="FLAT_LOANNUM")
	private String flatLoannum ;

	@Column(name="FLAT_LOANPAID")
	private Double flatLoanpaid ;

	@Column(name="FLAT_LOANYN")
	private String flatLoanyn ;

	@Column(name="FLAT_MAINTRATE")
	private Double flatMaintrate ;

	@Column(name="FLAT_MFLATBLDG")
	private String flatMflatbldg ;

	@Column(name="FLAT_MFLATNO")
	private String flatMflatno ;

	@Column(name="FLAT_MFLATWING")
	private String flatMflatwing ;

	@Column(name="FLAT_MPAIDDATE")
	private LocalDateTime flatMpaiddate ;

	@Column(name="FLAT_MPAIDREF")
	private String flatMpaidref ;

	@Column(name="FLAT_MPAIDYYMM")
	private String flatMpaidyymm ;

	@Column(name="FLAT_NOCDT")
	private LocalDateTime flatNocdt ;

	@Column(name="FLAT_NOCRCVDDATE")
	private LocalDateTime flatNocrcvddate ;

	@Column(name="FLAT_NOCTYPE")
	private String flatNoctype ;

	@Column(name="FLAT_OCCUPDATE")
	private LocalDateTime flatOccupdate ;

	@Column(name="FLAT_ORIGCOY")
	private String flatOrigcoy ;

	@Column(name="FLAT_ORIGSITE")
	private String flatOrigsite ;
	
	@Column(name="FLAT_OWNERID")
	private String flatOwnerid ;

	@Column(name="FLAT_OVERON")
	private LocalDateTime flatOveron ;

	@Column(name="FLAT_POACODE")
	private String flatPoacode ;

	@Column(name="FLAT_POANAME")
	private String flatPoaname ;

	@Column(name="FLAT_PROPTAX")
	private Double flatProptax ;

	@Column(name="FLAT_PSIND")
	private String flatPsind ;

	@Column(name="FLAT_RATESFT")
	private Double flatRatesft ;

	@Column(name="FLAT_REBATERFND")
	private Double flatRebaterfnd ;

	@Column(name="FLAT_REFUNDDATE")
	private LocalDateTime flatRefunddate ;

	@Column(name="FLAT_REMARKS")
	private String flatRemarks ;

	@Column(name="FLAT_SALESTATUS")
	private String flatSalestatus ;

	@Column(name="FLAT_SALETYPE")
	private String flatSaletype ;

	@Column(name="FLAT_SITE")
	private String flatSite ;

	@Column(name="FLAT_SOLDYN")
	private String flatSoldyn ;

	@Column(name="FLAT_STAMPDUTY")
	private Double flatStampduty ;

	@Column(name="FLAT_TODAY")
	private LocalDateTime flatToday ;

	@Column(name="FLAT_UFDISCOUNT")
	private String flatUfdiscount ;

	@Column(name="FLAT_USERID")
	private String flatUserid ;

	@Column(name="FLAT_XTRADATE")
	private LocalDateTime flatXtradate ;

	@Column(name="FLAT_XTRARFND")
	private Double flatXtrarfnd ;
		

}