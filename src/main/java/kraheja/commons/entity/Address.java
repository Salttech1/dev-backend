package kraheja.commons.entity;

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
 * The persistent class for the ADDRESS database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
		@Index(name = "ADR1", columnList = "adrAdowner ASC, adrAdsegment ASC, adrAdtype ASC, adrAdser ASC", unique = true),
		@Index(name = "ADR2", columnList = "adrAdsegment ASC, adrAdowner ASC, adrAdtype ASC, adrAdser ASC", unique = true),
})
@NamedStoredProcedureQuery(
		name = "SPR_DEPR_ADDRESS",
		procedureName = "SPR_DEPR_ADDRESS",
		parameters = {
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "owner", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "adtype", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "ser", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "topser", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "title", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "fname", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "mname", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "lname", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "ad1", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "ad2", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "ad3", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "ad4", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "ad5", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "town", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "city", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "state", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "country", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "pincode", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "phoneoff", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "adsegment", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "phoneres", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "fax", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "email", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "birthday", type = Date.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "site", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "userid", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "today", type = Date.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "origsite", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "insupd", type = String.class),
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "phonemobile", type = String.class),
		}
		)
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AddressCK addressCK;

	@Column(name="ADR_ADFNAME")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdfname;

	@Column(name="ADR_ADLINE1")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdline1;

	@Column(name="ADR_ADLINE2")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdline2;

	@Column(name="ADR_ADLINE3")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdline3;

	@Column(name="ADR_ADLINE4")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdline4;

	@Column(name="ADR_ADLINE5")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdline5;

	@Column(name="ADR_ADLNAME")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdlname;

	@Column(name="ADR_ADMNAME")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdmname;

	@Column(name="ADR_ADTITLE")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdtitle;

	@Column(name="ADR_ADTOPSER")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdtopser;

	@Column(name="ADR_BIRTHDAY")
	private LocalDateTime adrBirthday;

	@Column(name="ADR_CITY")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrCity;

	@Column(name="ADR_COUNTRY")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrCountry;

	@Column(name="ADR_EMAIL")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrEmail;

	@Column(name="ADR_FAX")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrFax;

	@Column(name="ADR_ORIGSITE")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrOrigsite;

	@Column(name="ADR_PHONEMOBILE")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrPhonemobile;

	@Column(name="ADR_PHONEOFF")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrPhoneoff;

	@Column(name="ADR_PHONERES")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrPhoneres;

	@Column(name="ADR_PINCODE")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrPincode;

	@Column(name="ADR_SITE")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrSite;

	@Column(name="ADR_STATE")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrState;

	@Column(name="ADR_TODAY")
	private LocalDateTime adrToday;

	@Column(name="ADR_TOWNSHIP")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrTownship;

	@Column(name="ADR_USERID")
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrUserid;
}