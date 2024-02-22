package kraheja.commons.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the DYNAPOP database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dynapop implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="DYN_POPID")
	private String dynPopid;
	
	@Column(name="DYN_BBC")
	private BigDecimal dynBbc;
	
	@Column(name="DYN_COLFORM1")
	private String dynColform1;

	@Column(name="DYN_COLFORM2")
	private String dynColform2;

	@Column(name="DYN_COLFORM3")
	private String dynColform3;

	@Column(name="DYN_COLFORM4")
	private String dynColform4;

	@Column(name="DYN_COLFORM5")
	private String dynColform5;

	@Column(name="DYN_COLHEAD1")
	private String dynColhead1;

	@Column(name="DYN_COLHEAD2")
	private String dynColhead2;

	@Column(name="DYN_COLHEAD3")
	private String dynColhead3;

	@Column(name="DYN_COLHEAD4")
	private String dynColhead4;

	@Column(name="DYN_COLHEAD5")
	private String dynColhead5;

	@Column(name="DYN_COLTYPE1")
	private String dynColtype1;

	@Column(name="DYN_COLTYPE2")
	private String dynColtype2;

	@Column(name="DYN_COLTYPE3")
	private String dynColtype3;

	@Column(name="DYN_COLTYPE4")
	private String dynColtype4;

	@Column(name="DYN_COLTYPE5")
	private String dynColtype5;

	@Column(name="DYN_COLUMN1")
	private String dynColumn1;

	@Column(name="DYN_COLUMN2")
	private String dynColumn2;

	@Column(name="DYN_COLUMN3")
	private String dynColumn3;

	@Column(name="DYN_COLUMN4")
	private String dynColumn4;

	@Column(name="DYN_COLUMN5")
	private String dynColumn5;

	@Column(name="DYN_CONDITION")
	private String dynCondition;

	@Column(name="DYN_DATABASE")
	private String dynDatabase;

	@Column(name="DYN_DISTINCT")
	private String dynDistinct;

	@Column(name="DYN_HEADER")
	private String dynHeader;

	@Column(name="DYN_NEUMONIC")
	private String dynNeumonic;

	@Column(name="DYN_ORDERBY")
	private String dynOrderby;

	@Column(name="DYN_REDLIB")
	private String dynRedlib;

	@Column(name="DYN_REDPOPUP")
	private String dynRedpopup;

	@Column(name="DYN_SEARCH")
	private BigDecimal dynSearch;

	@Column(name="DYN_SITE")
	private String dynSite;

	@Column(name="DYN_TABLE")
	private String dynTable;

	@Column(name="DYN_TODAY")
	private Date dynToday;

	@Column(name="DYN_USERID")
	private String dynUserid;
	
	@Column(name="DYN_SITEFILTER")
	private String dynSitefilter;
}