package kraheja.sales.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the FLATOWNER database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(indexes = {
		@Index(name = "FOWN1", columnList = "fownOwnerid ASC, fownBldgcode ASC, fownWing ASC, fownFlatnum ASC, fownOwnertype ASC", unique = true)
})

public class Flatowner implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FlatownerCK flatownerCK;

	@Column(name="FOWN_ADMINRATE")
	private Double fownAdminrate;

	@Column(name="FOWN_AUXIADMIN")
	private Double fownAuxiadmin;

	@Column(name="FOWN_AUXIMONTHS")
	private Integer fownAuximonths;

	@Column(name="FOWN_AUXIRATE")
	private Double fownAuxirate;

	@Column(name="FOWN_BILLMODE")
	private String fownBillmode;

	@Column(name="FOWN_CENCARD")
	private String fownCencard;

	@Column(name="FOWN_CITY")
	private String fownCity;

	@Column(name="FOWN_CUSTTYPE")
	private String fownCusttype;

	@Column(name="FOWN_ELECT")
	private Double fownElect;

	@Column(name="FOWN_FLOOR")
	private String fownFloor;

	@Column(name="FOWN_GSTNO")
	private String fownGstno;

	@Column(name="FOWN_INFRADMIN")
	private Double fownInfradmin;

	@Column(name="FOWN_INFRAMONTHS")
	private Integer fownInframonths;

	@Column(name="FOWN_INFRRATE")
	private Double fownInfrrate;

	@Column(name="FOWN_MAINTRATE")
	private Double fownMaintrate;

	@Column(name="FOWN_NAME")
	private String fownName;

	@Column(name="FOWN_NATAX")
	private Double fownNatax;

	@Column(name="FOWN_NREACNUM")
	private String fownNreacnum;

	@Column(name="FOWN_NREBANK")
	private String fownNrebank;

	@Column(name="FOWN_NRIIPI7")
	private String fownNriipi7;

	@Column(name="FOWN_NRINAT")
	private String fownNrinat;

	@Column(name="FOWN_NRIPASS")
	private String fownNripass;

	@Column(name="FOWN_NRIPASSISS")
	private String fownNripassiss;

	@Column(name="FOWN_NRIPEDATE")
	private LocalDate fownNripedate;

	@Column(name="FOWN_NRIPNAT")
	private String fownNripnat;

	@Column(name="FOWN_NRIPPIDATE")
	private LocalDate fownNrippidate;

	@Column(name="FOWN_NRIPROF")
	private String fownNriprof;

	@Column(name="FOWN_NRITELOFF")
	private String fownNriteloff;

	@Column(name="FOWN_NRITELRES")
	private String fownNritelres;

	@Column(name="FOWN_NROACNUM")
	private String fownNroacnum;

	@Column(name="FOWN_NROBANK")
	private String fownNrobank;

	@Column(name="FOWN_OGENDMM")
	private String fownOgendmm;

	@Column(name="FOWN_OGINTPAID")
	private Double fownOgintpaid;

	@Column(name="FOWN_OGMONTHS")
	private Integer fownOgmonths;

	@Column(name="FOWN_OGSTARTMM")
	private String fownOgstartmm;

	@Column(name="FOWN_ORIGSITE")
	private String fownOrigsite;

	@Column(name="FOWN_PANNO")
	private String fownPanno;

	@Column(name="FOWN_POANAT")
	private String fownPoanat;

	@Column(name="FOWN_POAPASS")
	private String fownPoapass;

	@Column(name="FOWN_POAPASSISS")
	private String fownPoapassiss;

	@Column(name="FOWN_POAPPIDATE")
	private LocalDate fownPoappidate;

	@Column(name="FOWN_POAPROF")
	private String fownPoaprof;

	@Column(name="FOWN_RELATION")
	private String fownRelation;

	@Column(name="FOWN_SITE")
	private String fownSite;

	@Column(name="FOWN_TITLE")
	private String fownTitle;

	@Column(name="FOWN_TODAY")
	private LocalDateTime fownToday;

	@Column(name="FOWN_TOWNSHIP")
	private String fownTownship;

	@Column(name="FOWN_USERID")
	private String fownUserid;

	@Column(name="FOWN_VIPYN")
	private String fownVipyn;

	@Column(name="FOWN_WATER")
	private Double fownWater;
	
	@Column(name="fown_aadharno")
	private String fownAadharno;

}