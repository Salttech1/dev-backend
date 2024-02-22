package kraheja.adminexp.vehicleexp.dataentry.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the ADMEXPH database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "ADMH_CERT1", columnList = "admhCertnum Asc, admhCoy Asc", unique = true)
})

public class Admexph implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdmexphCK admexphCK;

	@Column(name="ADMH_AVERAGE")
	private Double admhAverage ;

	@Column(name="ADMH_BILLNO")
	private String admhBillno ;

	@Column(name="ADMH_CERTAMOUNT")
	private Double admhCertamount ;

	@Column(name="ADMH_CERTDATE")
	private LocalDate admhCertdate ;

	@Column(name="ADMH_CERTREVNUM")
	private Double admhCertrevnum ;

	@Column(name="ADMH_CERTSTATUS")
	private String admhCertstatus ;
	
	@Column(name="ADMH_CERTTYPE")
	private String admhCerttype ; 

	@Column(name="ADMH_EMETERRED")
	private Double admhEmeterred ;

	@Column(name="ADMH_EQUIPID")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String admhEquipid ;

	@Column(name="ADMH_ESTIMATEDKM")
	private Double admhEstimatedkm ;

	@Column(name="ADMH_GASQTY")
	private Double admhGasqty ;

	@Column(name="ADMH_GSTYN")
	private String admhGstyn ;

	@Column(name="ADMH_METERID")
	private String admhMeterid ;

	@Column(name="ADMH_PARTYBILLDATE")
	private LocalDate admhPartybilldate ;

	@Column(name="ADMH_PARTYBILLREF")
	private String admhPartybillref ;

	@Column(name="ADMH_PARTYCODE")
	private String admhPartycode ;

	@Column(name="ADMH_PARTYTYPE")
	private String admhPartytype ;

	@Column(name="ADMH_PASSEDON")
	private LocalDate admhPassedon ;

	@Column(name="ADMH_PAYAMOUNT")
	private Double admhPayamount ;

	@Column(name="ADMH_PAYDATE")
	private LocalDate admhPaydate ;

	@Column(name="ADMH_PAYREF")
	private String admhPayref ;

	@Column(name="ADMH_PRINTED")
	private Double admhPrinted ;

	@Column(name="ADMH_PRINTEDON")
	private LocalDate admhPrintedon ;

	@Column(name="ADMH_PROP")
	private String admhProp ;

	@Column(name="ADMH_PRV_AMT")
	private Double admhPrv_Amt ;

	@Column(name="ADMH_PRV_CERTNUM")
	private String admhPrv_Certnum ;

	@Column(name="ADMH_PRV_DATE")
	private LocalDate admhPrv_Date ;

	@Column(name="ADMH_PRV_TYPE")
	private String admhPrv_Type ;

	@Column(name="ADMH_REMARKS")
	private String admhRemarks ;

	@Column(name="ADMH_SITE")
	private String admhSite ;

	@Column(name="ADMH_SMETERRED")
	private Double admhSmeterred ;

	@Column(name="ADMH_SOCID")
	private String admhSocid ;

	@Column(name="ADMH_TODAY")
	private LocalDateTime admhToday ;

	@Column(name="ADMH_TRANSER")
	private String admhTranser ;

	@Column(name="ADMH_T_PAYMENT")
	private Double admhT_Payment ;

	@Column(name="ADMH_USERID")
	private String admhUserid ;

}