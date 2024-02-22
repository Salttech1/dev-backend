package kraheja.commons.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the EPWORKS database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EPWORKS1", columnList = "epWorkcode Asc, epAworkcode Asc, epClosedate Asc, epOpendate Asc", unique = true)
})

public class Epworks implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EpworksCK epworksCK;

	@Column(name="EP_ACMAJOR")
	private String epAcmajor ;

	@Column(name="EP_ACNAME")
	private String epAcname ;

	@Column(name="EP_ALLOWDUPDUR")
	private String epAllowdupdur ;

	@Column(name="EP_AWORKNAME")
	private String epAworkname ;

	@Column(name="EP_COSTCLASS")
	private String epCostclass ;

	@Column(name="EP_LINKREQYN")
	private String epLinkreqyn ;

	@Column(name="EP_MAINGROUP")
	private String epMaingroup ;

	@Column(name="EP_MATCODE")
	private String epMatcode ;

	@Column(name="EP_PAYMODE")
	private String epPaymode ;

	@Column(name="EP_PRINTGROUP")
	private String epPrintgroup ;

	@Column(name="EP_SACCODE")
	private String epSaccode ;

	@Column(name="EP_SITE")
	private String epSite ;

	@Column(name="EP_TDSACMAJOR")
	private String epTdsacmajor ;

	@Column(name="EP_TODAY")
	private LocalDateTime epToday ;

	@Column(name="EP_USERID")
	private String epUserid ;

	@Column(name="EP_WORKGROUP")
	private String epWorkgroup ;

	@Column(name="EP_WORKNAME")
	private String epWorkname ;

}