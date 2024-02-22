package kraheja.enggsys.entity;


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
 * The persistent class for the LCAUTHBOE database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "LCAUTHBOE1", columnList = "lcabAuthnum Asc, lcabSrno Asc", unique = true)
})

public class Lcauthboe implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LcauthboeCK lcauthboeCK;

	@Column(name="LCAB_BLDGCODE")
	private String lcabBldgcode ;

	@Column(name="LCAB_BOEDATE")
	private LocalDate lcabBoedate ;

	@Column(name="LCAB_BOENO")
	private String lcabBoeno ;

	@Column(name="LCAB_COY")
	private String lcabCoy ;

	@Column(name="LCAB_DOCUMENTDATE")
	private LocalDate lcabDocumentdate ;

	@Column(name="LCAB_DOCUMENTNO")
	private String lcabDocumentno ;

	@Column(name="LCAB_DUTYFREEDATE")
	private LocalDate lcabDutyfreedate ;

	@Column(name="LCAB_DUTYFREENO")
	private String lcabDutyfreeno ;

	@Column(name="LCAB_EPCGDATE")
	private LocalDate lcabEpcgdate ;

	@Column(name="LCAB_EPCGNO")
	private String lcabEpcgno ;

	@Column(name="LCAB_INSPECTIONDATE")
	private LocalDate lcabInspectiondate ;

	@Column(name="LCAB_INSTCERTSUBMDT")
	private LocalDate lcabInstcertsubmdt ;

	@Column(name="LCAB_LASTSHIPMENTDATE")
	private LocalDate lcabLastshipmentdate ;

	@Column(name="LCAB_LCNO")
	private String lcabLcno ;

	@Column(name="LCAB_ORIGSITE")
	private String lcabOrigsite ;

	@Column(name="LCAB_PENDINGITEMS")
	private Integer lcabPendingitems ;

	@Column(name="LCAB_PROJECT")
	private String lcabProject ;

	@Column(name="LCAB_SHIPDOCRECDDATE")
	private LocalDate lcabShipdocrecddate ;

	@Column(name="LCAB_SITE")
	private String lcabSite ;

	@Column(name="LCAB_TODAY")
	private LocalDateTime lcabToday ;

	@Column(name="LCAB_USERID")
	private String lcabUserid ;

}