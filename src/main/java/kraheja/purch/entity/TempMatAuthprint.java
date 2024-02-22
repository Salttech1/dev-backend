package kraheja.purch.entity;
import java.io.Serializable;
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
 * The persistent class for the TEMP_MAT_AUTHPRINT database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "TEMP_MAT_AUTHPRINT1", columnList = "authAuthnum Asc, Sessid Asc, autdSuppbillno Asc", unique = true)
})

public class TempMatAuthprint implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TempMatAuthprintCK tempmatauthprintCK;

	@Column(name="ADDRESS1")
	private String Address1 ;

	@Column(name="ADDRESS2")
	private String Address2 ;

	@Column(name="AMT_IN_WORDS")
	private String amtIn_Words ;
	
	@Column(name="PBLH_SER")
	private String pblhSer ;
	
	@Column(name="PBLH_AUTHNUM")
	private String pblhAuthnum ;
	
	@Column(name="AUTD_AUTHNUM")
	private String autdAuthnum ;

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
	
	@Column(name="AUTH_AUTHTYPE")
	private String authAuthtype ;
	
	@Column(name="AUTH_PARTYCODE")
	private String authPartycode;

	@Column(name="AUTD_RELRETAMT")
	private Double autdRelretamt ;

	@Column(name="AUTD_RETAINAMT")
	private Double autdRetainamt ;

	@Column(name="AUTD_RETENTIONADJ")
	private Double autdRetentionadj ;

	@Column(name="AUTD_SUPPBILLDT")
	private LocalDate autdSuppbilldt ;

	@Column(name="AUTH_AMT")
	private Double authAmt ;

	@Column(name="AUTH_AUTHSTATUS")
	private String authAuthstatus ;

	@Column(name="AUTH_STAT")
	private String authStat ;

	@Column(name="BLDG_NAME")
	private String bldgName ;

	@Column(name="CERT_STAT")
	private String certStat ;

	@Column(name="CF_BLDG_WORK")
	private String cfBldg_Work ;

	@Column(name="COY_NAME")
	private String coyName ;

	@Column(name="CURRDATE")
	private LocalDate Currdate ;

	@Column(name="MATGROUP_NAME")
	private String matgroupName ;

	@Column(name="NO_OF_PRINT")
	private Integer noOf_Print ;

	@Column(name="PARTY_NAME")
	private String partyName ;

	@Column(name="PAY_AMT")
	private Double payAmt ;

	@Column(name="PAY_COVER")
	private String payCover ;

	@Column(name="PBLD_CESER")
	private String pbldCeser ;

	@Column(name="PBLH_RETAINOS")
	private Double pblhRetainos ;

	@Column(name="TOTCERTAMT")
	private Double Totcertamt ;

	@Column(name="TOT_ADJVAMT")
	private Double totAdjvamt ;

	@Column(name="USERID")
	private String Userid ;

}