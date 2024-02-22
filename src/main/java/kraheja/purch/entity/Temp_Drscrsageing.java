package kraheja.purch.entity;

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
 * The persistent class for the TEMP_DRSCRSAGEING database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "TEMP_DRSCRSAGEING", columnList = "tempSesid Asc, tempSrno Asc, tempPartycode Asc", unique = true)
})

public class Temp_Drscrsageing implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TempDrscrsageingCK tempDrscrsageingCK;

	@Column(name="TEMP_0TO365")
	private Double temp0to365 ;

	@Column(name="TEMP_366TO1095")
	private Double temp366to1095 ;

	@Column(name="TEMP_ABOVE1095")
	private Double tempAbove1095 ;

	@Column(name="TEMP_ABOVE180")
	private Double tempAbove180 ;

	@Column(name="TEMP_ADVANCE")
	private Double tempAdvance ;

	@Column(name="TEMP_BILLDATE")
	private LocalDate tempBilldate ;

	@Column(name="TEMP_BILLNO")
	private String tempBillno ;

	@Column(name="TEMP_IPADDRESS")
	private String tempIpaddress ;

	@Column(name="TEMP_MACHINENAME")
	private String tempMachinename ;

	@Column(name="TEMP_MODIFIEDON")
	private LocalDateTime tempModifiedon ;

	@Column(name="TEMP_MODULE")
	private String tempModule ;

	@Column(name="TEMP_RETENTION")
	private Double tempRetention ;

	@Column(name="TEMP_SITE")
	private String tempSite ;

	@Column(name="TEMP_TRANTYPE")
	private String tempTrantype ;

	@Column(name="TEMP_USERID")
	private String tempUserid ;

}