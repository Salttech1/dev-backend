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
 * The persistent class for the OUTRATE database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "OUTR1", columnList = "outrBldgcode Asc, outrFlatnum Asc, outrWing Asc, outrStartdate Asc", unique = true)
//		@Index(name = "OUTR1", columnList = "outrBldgcode Asc, outrFlatnum Asc, outrWing Asc", unique = true)
})

public class Outrate implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OutrateCK outrateCK;

	@Column(name="OUTR_ADMINCHARGES")
	private Double outrAdmincharges ;

	@Column(name="OUTR_AUXIADMIN")
	private Double outrAuxiadmin ;

	@Column(name="OUTR_AUXIRATE")
	private Double outrAuxirate ;

	@Column(name="OUTR_AUXI_TDS")
	private Double outrAuxi_Tds ;

	@Column(name="OUTR_BILLTYPE")
	private String outrBilltype ;

	@Column(name="OUTR_ELECT")
	private Double outrElect ;

	@Column(name="OUTR_ENDDATE")
	private String outrEnddate ;

	@Column(name="OUTR_INFRA")
	private Double outrInfra ;

	@Column(name="OUTR_INFRADMIN")
	private Double outrInfradmin ;

	@Column(name="OUTR_INFRA_TDS")
	private Double outrInfra_Tds ;

	@Column(name="OUTR_INFRRATE")
	private Double outrInfrrate ;

	@Column(name="OUTR_MAINT")
	private Double outrMaint ;

	@Column(name="OUTR_MAINT_TDS")
	private Double outrMaint_Tds ;

	@Column(name="OUTR_NATAX")
	private Double outrNatax ;

	@Column(name="OUTR_OLDADMIN_NOTUSED")
	private Double outrOldadmin_Notused ;

	@Column(name="OUTR_PROPONBUCAAREA")
	private String outrProponbucaarea ;

	@Column(name="OUTR_PROPRATE")
	private Double outrProprate ;

	@Column(name="OUTR_PROPRATESQFT")
	private Double outrPropratesqft ;

	@Column(name="OUTR_RATE")
	private Double outrRate ;

	@Column(name="OUTR_SITE")
	private String outrSite ;

	@Column(name="OUTR_TODAY")
	private LocalDateTime outrToday ;

	@Column(name="OUTR_USERID")
	private String outrUserid ;

	@Column(name="OUTR_WATER")
	private Double outrWater ;

}