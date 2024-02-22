package kraheja.adminexp.billing.dataentry.entity;


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
 * The persistent class for the INTERCOYBILLDETAIL database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "INTERCOYBILLDETAIL1", columnList = "icbedGroupinvoiceno Asc, icbedInvoiceno Asc, icbedSrno Asc", unique = true)
})

public class Intercoybilldetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IntercoybilldetailCK intercoybilldetailCK;

	@Column(name="ICBED_ACAMOUNT")
	private Double icbedAcamount ;

	@Column(name="ICBED_ACMAJOR")
	private String icbedAcmajor ;

	@Column(name="ICBED_CGSTAMT")
	private Double icbedCgstamt ;

	@Column(name="ICBED_CGSTPER")
	private Integer icbedCgstper ;

	@Column(name="ICBED_HSNCODE")
	private String icbedHsncode ;

	@Column(name="ICBED_IGSTAMT")
	private Integer icbedIgstamt ;

	@Column(name="ICBED_IGSTPER")
	private Integer icbedIgstper ;

	@Column(name="ICBED_MINOR")
	private String icbedMinor ;

	@Column(name="ICBED_MINTYPE")
	private String icbedMintype ;

	@Column(name="ICBED_ORIGSITE")
	private String icbedOrigsite ;

	@Column(name="ICBED_PARTYSHAREDPER")
	private Integer icbedPartysharedper ;

	@Column(name="ICBED_PARTYTYPE")
	private String icbedPartytype ;

	@Column(name="ICBED_SGSTAMT")
	private Double icbedSgstamt ;

	@Column(name="ICBED_SGSTPER")
	private Integer icbedSgstper ;

	@Column(name="ICBED_SITE")
	private String icbedSite ;

	@Column(name="ICBED_TODAY")
	private LocalDateTime icbedToday ;

	@Column(name="ICBED_TRANAMT")
	private Double icbedTranamt ;

	@Column(name="ICBED_UGSTAMT")
	private Integer icbedUgstamt ;

	@Column(name="ICBED_UGSTPER")
	private Integer icbedUgstper ;

	@Column(name="ICBED_USERID")
	private String icbedUserid ;

}