package kraheja.commons.entity;

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
 * The persistent class for the OUTCHQ database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "OUTCHQ1", columnList = "outchqProprietor Asc, outchqCoy Asc, outchqBank Asc, outchqNum Asc", unique = true)
})

public class Outchq implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OutchqCK outchqCK;
	
	@Column(name="OUTCHQ_TRANSER")
	private String outchqTranser ;

	@Column(name="OUTCHQ_SERIES")
	private String outchqSeries ; 
	
	@Column(name="OUTCHQ_AMOUNT")
	private Double outchqAmount ;

	@Column(name="OUTCHQ_BUNUM")
	private Double outchqBunum ;

	@Column(name="OUTCHQ_CANCELDATE")
	private LocalDateTime outchqCanceldate ;

	@Column(name="OUTCHQ_ORIGSITE")
	private String outchqOrigsite ;

	@Column(name="OUTCHQ_ORIGSYS")
	private String outchqOrigsys ;

	@Column(name="OUTCHQ_PARTYCODE")
	private String outchqPartycode ;

	@Column(name="OUTCHQ_PARTYTYPE")
	private String outchqPartytype ;

	@Column(name="OUTCHQ_PAYDATE")
	private LocalDate outchqPaydate ;

	@Column(name="OUTCHQ_PAYMODE")
	private String outchqPaymode ;

	@Column(name="OUTCHQ_PAYREF")
	private String outchqPayref ;

	@Column(name="OUTCHQ_PRINTED")
	private Double outchqPrinted ;

	@Column(name="OUTCHQ_PRINTEDON")
	private LocalDateTime outchqPrintedon ;

	@Column(name="OUTCHQ_RECONDATE")
	private LocalDateTime outchqRecondate ;

	@Column(name="OUTCHQ_RECONSTMT")
	private String outchqReconstmt ;

	@Column(name="OUTCHQ_SITE")
	private String outchqSite ;

	@Column(name="OUTCHQ_TODAY")
	private LocalDateTime outchqToday ;

	@Column(name="OUTCHQ_USERID")
	private String outchqUserid ;

}