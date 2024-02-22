package kraheja.commons.entity;

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
import lombok.ToString;


/**
 * The persistent class for the GLCHART database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(indexes = {
		@Index(name = "CHART1", columnList = "chartAcnum ASC, chartClosedate ASC", unique = true),
})
public class Glchart implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private GlchartCK glChartCK;

	@Column(name="CHART_ACNAME")
	private String chartAcname;

	@Column(name="CHART_ACTYPE")
	private String chartActype;

	@Column(name="CHART_BSGROUP")
	private String chartBsgroup;

	@Column(name="CHART_BSSUBGROUP")
	private String chartBssubgroup;

	@Column(name="CHART_BSSUBSUBGROUP")
	private String chartBssubsubgroup;

	@Column(name="CHART_CFPAYGROUP")
	private String chartCfpaygroup;

	@Column(name="CHART_CFRECGROUP")
	private String chartCfrecgroup;

	@Column(name="CHART_CLUBGLYN")
	private String chartClubglyn;

	@Column(name="CHART_CLUBYN")
	private String chartClubyn;

	@Column(name="CHART_CONSREP")
	private String chartConsrep;

	@Column(name="CHART_CONTROLAC")
	private String chartControlac;

	@Column(name="CHART_CONTTDS")
	private String chartConttds;

	@Column(name="CHART_DETAILJV")
	private String chartDetailjv;

	@Column(name="CHART_GROUPCODE")
	private String chartGroupcode;

	@Column(name="CHART_INTTXNTYPE")
	private String chartInttxntype;

	@Column(name="CHART_JVALLOWEDYN")
	private String chartJvallowedyn;

	@Column(name="CHART_LEVEL")
	private Double chartLevel;

	@Column(name="CHART_MB1LEN")
	private Double chartMb1len;

	@Column(name="CHART_MB1START")
	private Double chartMb1start;

	@Column(name="CHART_MINORYN")
	private String chartMinoryn;

	@Column(name="CHART_MISCOLLECT")
	private String chartMiscollect;

	@Column(name="CHART_MISCOST")
	private String chartMiscost;

	@Column(name="CHART_MISGROUPC")
	private String chartMisgroupc;

	@Column(name="CHART_MISGTYPE")
	private String chartMisgtype;

	@Column(name="CHART_MISINTEREST")
	private String chartMisinterest;

	@Column(name="CHART_MISLAND")
	private String chartMisland;

	@Column(name="CHART_MISSGROUPC")
	private String chartMissgroupc;

	@Column(name="CHART_MISTXNTYPE")
	private String chartMistxntype;

	@Column(name="CHART_NOTES")
	private String chartNotes;

	@Column(name="CHART_OPENDATE")
	private LocalDateTime chartOpendate;

	@Column(name="CHART_PB1LEN")
	private Double chartPb1len;

	@Column(name="CHART_PB1START")
	private Double chartPb1start;

	@Column(name="CHART_PB2LEN")
	private Double chartPb2len;

	@Column(name="CHART_PB2START")
	private Double chartPb2start;

	@Column(name="CHART_PGROUPC")
	private String chartPgroupc;

	@Column(name="CHART_POSTGLONLY")
	private String chartPostglonly;

	@Column(name="CHART_POSTPROJONLY")
	private String chartPostprojonly;

	@Column(name="CHART_RGROUPC")
	private String chartRgroupc;

	@Column(name="CHART_SCHEDULE")
	private Double chartSchedule;

	@Column(name="CHART_SITE")
	private String chartSite;

	@Column(name="CHART_TDSACYN")
	private String chartTdsacyn;

	@Column(name="CHART_TODAY")
	private LocalDateTime chartToday;

	@Column(name="CHART_USERID")
	private String chartUserid;

	@Column(name="CHART_VALIDMINORS")
	private String chartValidminors;

	@Column(name="CHART_VALIDPARTIES")
	private String chartValidparties;
}