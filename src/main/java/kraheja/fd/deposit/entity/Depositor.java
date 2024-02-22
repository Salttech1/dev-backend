package kraheja.fd.deposit.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the DEPOSITOR database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
		@Index(name = "DEPTR1", columnList = "deptrCoy ASC, deptrDepositor ASC", unique = true),
})
@NamedStoredProcedureQuery(
		name = "SPR_DEPR_INSERTDETAILS",
		procedureName = "SPR_DEPR_INSERTDETAILS",
		parameters = {
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "depositor", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "title", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "name", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "proprietor", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "coy", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "depamount", type = Double.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "grossint", type = Double.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "accint", type = Double.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "tds", type = Double.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "remarks", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "city", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "site", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "userid", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "today", type = Date.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "origsite", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "deptype", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "taxyn", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "tds15hyn", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "tds15gyn", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "intpayedytd", type = Double.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "taxpaidytd", type = Double.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "taxrecytd", type = Double.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "clubref", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "pannum1", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "pannum2", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "birthdate", type = Date.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "insupd", type = String.class),
		}
		)
public class Depositor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private DepositorCK depositorCK; 

	@Column(name="DEPTR_15HRECD")
	private LocalDateTime deptr15hrecd;

	@Column(name="DEPTR_15HSUBD")
	private LocalDateTime deptr15hsubd;

	@Column(name="DEPTR_ACCINT")
	private Double deptrAccint;

	@Column(name="DEPTR_BIRTHDATE")
	private LocalDateTime deptrBirthdate;

	@Column(name="DEPTR_CITY")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrCity;

	@Column(name="DEPTR_CLUBREF")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrClubref;

	@Column(name="DEPTR_DEPAMOUNT")
	private Double deptrDepamount;

	@Column(name="DEPTR_DEPTYPE")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrDeptype;

	@Column(name="DEPTR_GROSSINT")
	private Double deptrGrossint;

	@Column(name="DEPTR_INTPAIDYTD")
	private Double deptrIntpaidytd;

	@Column(name="DEPTR_NAME")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrName;

	@Column(name="DEPTR_ORIGSITE")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrOrigsite;

	@Column(name="DEPTR_PANUM1")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrPanum1;

	@Column(name="DEPTR_PANUM2")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrPanum2;

	@Column(name="DEPTR_PROPRIETOR")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrProprietor;

	@Column(name="DEPTR_REMARKS")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrRemarks;

	@Column(name="DEPTR_SITE")
	private String deptrSite;

	@Column(name="DEPTR_TAXALWAYSYN")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrTaxalwaysyn;

	@Column(name="DEPTR_TAXPAIDYTD")
	private Double deptrTaxpaidytd;

	@Column(name="DEPTR_TAXRECYTD")
	private Double deptrTaxrecytd;

	@Column(name="DEPTR_TDS")
	private Double deptrTds;

	@Column(name="DEPTR_TDS15GYN")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrTds15gyn;

	@Column(name="DEPTR_TDS15HYN")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrTds15hyn;

	@Column(name="DEPTR_TITLE")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrTitle;

	@Column(name="DEPTR_TODAY")
	private LocalDateTime deptrToday;

	@Column(name="DEPTR_USERID")
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrUserid;
}