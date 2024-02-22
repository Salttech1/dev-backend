package kraheja.arch.projbldg.dataentry.entity;

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
 * The persistent class for the MAILINFO database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "MINF1", columnList = "minfBldgcode Asc", unique = true)
})

public class Mailinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MailinfoCK mailinfoCK;

	@Column(name="BLDG_AMENITY1")
	private String bldgAmenity1 ;

	@Column(name="BLDG_AMENITY2")
	private String bldgAmenity2 ;

	@Column(name="BLDG_AMENITY3")
	private String bldgAmenity3 ;

	@Column(name="BLDG_AMENITY4")
	private String bldgAmenity4 ;

	@Column(name="BLDG_AMENITY5")
	private String bldgAmenity5 ;

	@Column(name="MINF_AREA1")
	private String minfArea1 ;

	@Column(name="MINF_AREA2")
	private String minfArea2 ;

	@Column(name="MINF_AREA3")
	private String minfArea3 ;

	@Column(name="MINF_CITY")
	private String minfCity ;

	@Column(name="MINF_CONFIG1")
	private String minfConfig1 ;

	@Column(name="MINF_CONFIG2")
	private String minfConfig2 ;

	@Column(name="MINF_CONFIG3")
	private String minfConfig3 ;

	@Column(name="MINF_CONFIG4")
	private String minfConfig4 ;

	@Column(name="MINF_COY")
	private String minfCoy ;

	@Column(name="MINF_ORIGSITE")
	private String minfOrigsite ;

	@Column(name="MINF_OTHEREX1")
	private String minfOtherex1 ;

	@Column(name="MINF_OTHEREX2")
	private String minfOtherex2 ;

	@Column(name="MINF_POSSDATE")
	private String minfPossdate ;

	@Column(name="MINF_PROJECT")
	private String minfProject ;

	@Column(name="MINF_RATE1")
	private String minfRate1 ;

	@Column(name="MINF_RATE2")
	private String minfRate2 ;

	@Column(name="MINF_RATE3")
	private String minfRate3 ;

	@Column(name="MINF_REMARK1")
	private String minfRemark1 ;

	@Column(name="MINF_REMARK2")
	private String minfRemark2 ;

	@Column(name="MINF_REMARK3")
	private String minfRemark3 ;

	@Column(name="MINF_SITE")
	private String minfSite ;

	@Column(name="MINF_TENURE")
	private String minfTenure ;

	@Column(name="MINF_TERM1")
	private String minfTerm1 ;

	@Column(name="MINF_TERM2")
	private String minfTerm2 ;

	@Column(name="MINF_TERM3")
	private String minfTerm3 ;

	@Column(name="MINF_TERM4")
	private String minfTerm4 ;

	@Column(name="MINF_TERM5")
	private String minfTerm5 ;

	@Column(name="MINF_TERM6")
	private String minfTerm6 ;

	@Column(name="MINF_TODAY")
	private LocalDateTime minfToday ;

	@Column(name="MINF_TOWNSHIP")
	private String minfTownship ;

	@Column(name="MINF_USERID")
	private String minfUserid ;

	@Column(name="MINF_VALIDITY1")
	private String minfValidity1 ;

	@Column(name="MINF_VALIDITY2")
	private String minfValidity2 ;

}