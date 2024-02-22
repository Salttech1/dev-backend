package kraheja.adminexp.vehicleexp.dataentry.entity;

import java.io.Serializable;
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
 * The persistent class for the EQUIP database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EQP1", columnList = "eqpEqptype Asc, eqpEqpnum Asc", unique = true)
})

public class Equip implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EquipCK equipCK;

	@Column(name="EQP_ALLOTTEDTO")
	private String eqpAllottedto ;

	@Column(name="EQP_AUTHORISATIONVALIDTILL")
	private LocalDate eqpAuthorisationvalidtill ;

	@Column(name="EQP_AVGLAST")
	private Double eqpAvglast ;

	@Column(name="EQP_BATTERYCHANGED")
	private LocalDate eqpBatterychanged ;

	@Column(name="EQP_BATTERYEXPIRY")
	private LocalDate eqpBatteryexpiry ;

	@Column(name="EQP_BMCTAXEXP")
	private LocalDate eqpBmctaxexp ;

	@Column(name="EQP_BOOKVALUE")
	private Double eqpBookvalue ;

	@Column(name="EQP_CHASISNO")
	private String eqpChasisno ;

	@Column(name="EQP_COY")
	private String eqpCoy ;

	@Column(name="EQP_DISPAADHAAR")
	private String eqpDispaadhaar ;

	@Column(name="EQP_DISPADDRESS")
	private String eqpDispaddress ;

	@Column(name="EQP_DISPCHEQUEDATE")
	private LocalDate eqpDispchequedate ;

	@Column(name="EQP_DISPCHEQUENO")
	private String eqpDispchequeno ;

	@Column(name="EQP_DISPCONTACTNO")
	private String eqpDispcontactno ;

	@Column(name="EQP_DISPDATE")
	private LocalDate eqpDispdate ;

	@Column(name="EQP_DISPNAME")
	private String eqpDispname ;

	@Column(name="EQP_DISPPAN")
	private String eqpDisppan ;

	@Column(name="EQP_DISPVALUE")
	private Double eqpDispvalue ;

	@Column(name="EQP_EMIAMOUNT")
	private Double eqpEmiamount ;

	@Column(name="EQP_EMIENDDATE")
	private LocalDate eqpEmienddate ;

	@Column(name="EQP_EMISTARTDATE")
	private LocalDate eqpEmistartdate ;

	@Column(name="EQP_ENGINECC")
	private String eqpEnginecc ;

	@Column(name="EQP_ENGINENO")
	private String eqpEngineno ;

	@Column(name="EQP_FITNESSVALIDTILL")
	private LocalDate eqpFitnessvalidtill ;

	@Column(name="EQP_HPACANCELLEDON")
	private LocalDate eqpHpacancelledon ;

	@Column(name="EQP_HPAWITH")
	private String eqpHpawith ;

	@Column(name="EQP_INSCOMPANY")
	private String eqpInscompany ;

	@Column(name="EQP_INSEXPON")
	private LocalDate eqpInsexpon ;

	@Column(name="EQP_INSPOLICYNO")
	private String eqpInspolicyno ;

	@Column(name="EQP_KMSLIMIT")
	private Double eqpKmslimit ;

	@Column(name="EQP_LOANACNO")
	private String eqpLoanacno ;

	@Column(name="EQP_LOANAMOUNT")
	private Double eqpLoanamount ;

	@Column(name="EQP_LOANPAPERSIGNEDBY")
	private String eqpLoanpapersignedby ;

	@Column(name="EQP_LYMEXP")
	private Double eqpLymexp ;

	@Column(name="EQP_LYREXP")
	private Double eqpLyrexp ;

	@Column(name="EQP_NEXTMAINT")
	private LocalDate eqpNextmaint ;

	@Column(name="EQP_ORIGSITE")
	private String eqpOrigsite ;

	@Column(name="EQP_PERMITVALIDTILL")
	private LocalDate eqpPermitvalidtill ;

	@Column(name="EQP_PROP")
	private String eqpProp ;

	@Column(name="EQP_PURCDATE")
	private LocalDate eqpPurcdate ;

	@Column(name="EQP_RCBOOKSIGNEDBY")
	private String eqpRcbooksignedby ;

	@Column(name="EQP_REGISTRATIONAUTH")
	private String eqpRegistrationauth ;

	@Column(name="EQP_REGVALIDTILL")
	private LocalDate eqpRegvalidtill ;

	@Column(name="EQP_RTOTAXEXP")
	private String eqpRtotaxexp ;

	@Column(name="EQP_RTOTAXPAIDTILL")
	private LocalDate eqpRtotaxpaidtill ;

	@Column(name="EQP_RUNON")
	private String eqpRunon ;

	@Column(name="EQP_SERVEXPIRY")
	private LocalDate eqpServexpiry ;

	@Column(name="EQP_SERVICEDONE")
	private LocalDate eqpServicedone ;

	@Column(name="EQP_SERVSTATUS")
	private String eqpServstatus ;

	@Column(name="EQP_SITE")
	private String eqpSite ;

	@Column(name="EQP_SITEBLDG")
	private String eqpSitebldg ;

	@Column(name="EQP_TODATEMEXP")
	private Double eqpTodatemexp ;

	@Column(name="EQP_TODATEREXP")
	private Double eqpTodaterexp ;

	@Column(name="EQP_TODAY")
	private LocalDateTime eqpToday ;

	@Column(name="EQP_TUNEEXPIRY")
	private LocalDate eqpTuneexpiry ;

	@Column(name="EQP_TUNEUP")
	private LocalDate eqpTuneup ;

	@Column(name="EQP_TYMEXP")
	private Double eqpTymexp ;

	@Column(name="EQP_TYREXP")
	private Double eqpTyrexp ;

	@Column(name="EQP_USERID")
	private String eqpUserid ;

	@Column(name="EQP_VEHMAKE")
	private String eqpVehmake ;

	@Column(name="EQP_VEHTYPE")
	private String eqpVehtype ;

}