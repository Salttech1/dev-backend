package kraheja.purch.entity;

import java.io.Serializable;
import java.time.LocalDate;
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

/**
 * The persistent class for the AUTH_D database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "AUTD1", columnList = "autdAuthnum Asc, autdSuppbillno Asc", unique = true)
})

public class Auth_D implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Auth_DCK auth_dCK;
	
	@Column(name="AUTD_PARTYCODE")
	private String autdPartycode ; 

	@Column(name="AUTD_BLDGCODE")
	private String autdBldgcode ; 

	@Column(name="AUTD_MATGROUP")
	private String autdMatgroup ; 

	@Column(name="AUTD_ADVADJ")
	private Double autdAdvadj ;

	@Column(name="AUTD_AUTHAMOUNT")
	private Double autdAuthamount ;

	@Column(name="AUTD_AUTHQTY")
	private Double autdAuthqty ;

	@Column(name="AUTD_AUTHTDSAMT")
	private Double autdAuthtdsamt ;

	@Column(name="AUTD_AUTHTYPE")
	private String autdAuthtype ;

	@Column(name="AUTD_BILLTYPE")
	private String autdBilltype ;

	@Column(name="AUTD_MATCODE")
	private String autdMatcode ;

	@Column(name="AUTD_ORIGSITE")
	private String autdOrigsite ;

	@Column(name="AUTD_RELRETAMT")
	private Double autdRelretamt ;

	@Column(name="AUTD_RETAINAMT")
	private Double autdRetainamt ;

	@Column(name="AUTD_RETENTIONADJ")
	private Double autdRetentionadj ;

	@Column(name="AUTD_SITE")
	private String autdSite ;

	@Column(name="AUTD_SUPPBILLDT")
	private LocalDate autdSuppbilldt ;
	

	@Column(name="AUTD_TODAY")
	private LocalDateTime autdToday ;

	@Column(name="AUTD_USERID")
	private String autdUserid ;

}