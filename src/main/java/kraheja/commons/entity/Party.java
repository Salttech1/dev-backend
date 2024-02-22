package kraheja.commons.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
 * The persistent class for the PARTY database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Party implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PartyCK partyCk;

	@Column(name="PAR_AADHARNO")
	private String parAadharno;

	@Column(name="PAR_CINO")
	private String parCino;

	@Column(name="PAR_CITY")
	private String parCity;

	@Column(name="PAR_ESICNO")
	private String parEsicno;

	@Column(name="PAR_GSTNO")
	private String parGstno;

	@Column(name="PAR_LASTINTPAID")
	private LocalDateTime parLastintpaid;

	@Column(name="PAR_LTDCOYN")
	private String parLtdcoyn;

	@Column(name="PAR_O_STAXREGNCEN")
	private String parOStaxregncen;

	@Column(name="PAR_O_STAXREGNST")
	private String parOStaxregnst;

	@Column(name="PAR_OLDREFDATE")
	private LocalDateTime parOldrefdate;

	@Column(name="PAR_OPENDATE")
	private LocalDate parOpendate;

	@Column(name="PAR_PARTYCONSTT")
	private String parPartyconstt;

	@Column(name="PAR_PARTYNAME")
	private String parPartyname;

	@Column(name="PAR_PAYEEACNUM1")
	private String parPayeeacnum1;

	@Column(name="PAR_PAYEEACNUM2")
	private String parPayeeacnum2;

	@Column(name="PAR_PAYEEBANKCODE1")
	private String parPayeebankcode1;

	@Column(name="PAR_PAYEEBANKCODE2")
	private String parPayeebankcode2;

	@Column(name="PAR_PAYEEBANKNAME1")
	private String parPayeebankname1;

	@Column(name="PAR_PAYEEBANKNAME2")
	private String parPayeebankname2;

	@Column(name="PAR_PAYEEBRANCH1")
	private String parPayeebranch1;

	@Column(name="PAR_PAYEEBRANCH2")
	private String parPayeebranch2;

	@Column(name="PAR_PAYEEIFSC1")
	private String parPayeeifsc1;

	@Column(name="PAR_PAYEEIFSC2")
	private String parPayeeifsc2;

	@Column(name="PAR_PFNO")
	private String parPfno;

	@Column(name="PAR_PMTACNUM")
	private String parPmtacnum;

	@Column(name="PAR_PROFESSIONTAXNO")
	private String parProfessiontaxno;

	@Column(name="PAR_RCNUM")
	private String parRcnum;

	@Column(name="PAR_SERVICETAXNUM")
	private String parServicetaxnum;

	@Column(name="PAR_SITE")
	private String parSite;

	@Column(name="PAR_STAXREGNCEN")
	private String parStaxregncen;

	@Column(name="PAR_STAXREGNST")
	private String parStaxregnst;

	@Column(name="PAR_SUPPTYPE")
	private String parSupptype;

	@Column(name="PAR_TANNO")
	private String parTanno;

	@Column(name="PAR_TINNUM")
	private String parTinnum;

	@Column(name="PAR_TITLE")
	private String parTitle;

	@Column(name="PAR_TODAY")
	private LocalDateTime parToday;

	@Column(name="PAR_USERID")
	private String parUserid;

	@Column(name="PAR_VALIDMINOR")
	private String parValidminor;

	@Column(name="PAR_VALIDPARTY")
	private String parValidparty;

	@Column(name="PAR_VATCANCELDT")
	private LocalDateTime parVatcanceldt;

	@Column(name="PAR_VATEFFECTIVEFROM")
	private LocalDateTime parVateffectivefrom;

	@Column(name="PAR_VATNUM")
	private String parVatnum;

	@Column(name="PAR_AADHARPANLINKEDYN")
	private String parAadharPanLinkedYN;
}