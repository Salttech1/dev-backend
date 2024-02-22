package kraheja.fd.deposit.entity;

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
 * The persistent class for the FORM15HG database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "FORM15HG1", columnList = "formCoy Asc, formAcyear Asc, formDepositor Asc, formQuarter Asc", unique = true)
})

public class Form15hg implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Form15hgCK form15hgCK;

	@Column(name="FORM_AMTINCOMEPAID")
	private Double formAmtincomepaid ;

	@Column(name="FORM_ASSESSEDYN")
	private String formAssessedyn ;

	@Column(name="FORM_ASSESSYEAR")
	private String formAssessyear ;

	@Column(name="FORM_DATEDECLRECEIVED")
	private LocalDateTime formDatedeclreceived ;

	@Column(name="FORM_DATEINCOMEPAID")
	private LocalDateTime formDateincomepaid ;

	@Column(name="FORM_ESTIMATEDINCOME")
	private Double formEstimatedincome ;

	@Column(name="FORM_ESTIMATEDTOTINCOME")
	private Double formEstimatedtotincome ;

	@Column(name="FORM_INCOMEFORMFILED")
	private Double formIncomeformfiled ;

	@Column(name="FORM_ORIGSITE")
	private String formOrigsite ;

	@Column(name="FORM_SITE")
	private String formSite ;

	@Column(name="FORM_TODAY")
	private LocalDateTime formToday ;

	@Column(name="FORM_TOTALFORMFILED")
	private Double formTotalformfiled ;

	@Column(name="FORM_UNIQUEID")
	private String formUniqueid ;

	@Column(name="FORM_USERID")
	private String formUserid ;

}