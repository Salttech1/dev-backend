package kraheja.payroll.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDate;
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
 * The persistent class for the EMPPERSONAL database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPPERSONAL1", columnList = "eperEmpcode Asc, eperEffectivefrom Asc", unique = true)
})

public class Emppersonal implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmppersonalCK emppersonalCK;

	@Column(name="EPER_AADHAARNO")
	private String eperAadhaarno ;

	@Column(name="EPER_BIRTHDATE")
	private LocalDate eperBirthdate ;

	@Column(name="EPER_BLOODGRP")
	private String eperBloodgrp ;

	@Column(name="EPER_EFFECTIVEUPTO")
	private LocalDate eperEffectiveupto ;

	@Column(name="EPER_FULLNAME")
	private String eperFullname ;

	@Column(name="EPER_GENDER")
	private String eperGender ;

	@Column(name="EPER_HANDICAPYN")
	private String eperHandicapyn ;

	@Column(name="EPER_HEIGHT")
	private Double eperHeight ;

	@Column(name="EPER_HOBBIES")
	private String eperHobbies ;

	@Column(name="EPER_IPADDRESS")
	private String eperIpaddress ;

	@Column(name="EPER_MACHINENAME")
	private String eperMachinename ;

	@Column(name="EPER_MARITALSTAT")
	private String eperMaritalstat ;

	@Column(name="EPER_MODIFIEDON")
	private LocalDateTime eperModifiedon ;

	@Column(name="EPER_MODULE")
	private String eperModule ;

	@Column(name="EPER_MOTHERTONGUE")
	private String eperMothertongue ;

	@Column(name="EPER_NATIONALITY")
	private String eperNationality ;

	@Column(name="EPER_NOOFCHILDREN")
	private Integer eperNoofchildren ;

	@Column(name="EPER_PANNO")
	private String eperPanno ;

	@Column(name="EPER_PFUAN")
	private String eperPfuan ;

	@Column(name="EPER_PHOTOPATH")
	private String eperPhotopath ;

	@Column(name="EPER_RELIGION")
	private String eperReligion ;

	@Column(name="EPER_REMARK")
	private String eperRemark ;

	@Column(name="EPER_SITE")
	private String eperSite ;

	@Column(name="EPER_TITLE")
	private String eperTitle ;

	@Column(name="EPER_USERID")
	private String eperUserid ;

	@Column(name="EPER_WEDDINGDATE")
	private LocalDate eperWeddingdate ;

	@Column(name="EPER_WEIGHT")
	private Double eperWeight ;

}