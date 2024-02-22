package kraheja.adminexp.overheads.dataentry.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(indexes = {
		@Index(name = "CONSUMERNO", columnList = "ohdhConsumerno Asc", unique = true), 
		@Index(name = "OHCO1", columnList = "ohdhConnocode Asc", unique = true)
})

public class Overheadcons implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private OverheadconsCK overheadconsCK;
	
	@Column(name="OHDH_BILLCOY")
	private String ohdhBillcoy ;

	@Column(name="OHDH_BILLTYPE")
	private String ohdhBilltype ;

	@Column(name="OHDH_BLDGCODE")
	private String ohdhBldgcode ;

	@Column(name="OHDH_CONNO")
	@Type(type = "kraheja.commons.utils.CharType")
	private String ohdhConno ;

	@Column(name="OHDH_DEPOSITEAMT")
	private Double ohdhDepositeamt ;

	@Column(name="OHDH_FLATNUM")
	private String ohdhFlatnum ;

	@Column(name="OHDH_LOAD")
	private String ohdhLoad ;

	@Column(name="OHDH_LOCATION")
	private String ohdhLocation ;

	@Column(name="OHDH_PAYCOY")
	private String ohdhPaycoy ;

	@Column(name="OHDH_SITE")
	private String ohdhSite ;

	@Column(name="OHDH_STATUS")
	private String ohdhStatus ;

	@Column(name="OHDH_TMPMETERYN")
	private String ohdhTmpmeteryn ;

	@Column(name="OHDH_TODAY")
	private LocalDateTime ohdhToday ;

	@Column(name="OHDH_USERID")
	private String ohdhUserid ;

	@Column(name="OHDH_VACANTFLATYN")
	private String ohdhVacantflatyn ;
	
}
