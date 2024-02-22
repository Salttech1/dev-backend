package kraheja.adminexp.vehicleexp.dataentry.entity;

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
 * The persistent class for the ADMEXPD database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "ADMD_CERT1", columnList = "admdCertnum Asc, admdBunum Asc", unique = true)
})

public class Admexpd implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdmexpdCK admexpdCK;

	@Column(name="ADMD_BILLAMOUNT")
	private Double admdBillamount ;

	@Column(name="ADMD_BILLDATE")
	private LocalDate admdBilldate ;

	@Column(name="ADMD_BILLREF")
	private String admdBillref ;

	@Column(name="ADMD_CGST")
	private Double admdCgst ;

	@Column(name="ADMD_CGSTPERC")
	private Double admdCgstperc ;

	@Column(name="ADMD_DURATIONFROM")
	private LocalDate admdDurationfrom ;

	@Column(name="ADMD_DURATIONUPTO")
	private LocalDate admdDurationupto ;

	@Column(name="ADMD_HSMSCODE")
	private String admdHsmscode ;

	@Column(name="ADMD_IGST")
	private Double admdIgst ;

	@Column(name="ADMD_IGSTPERC")
	private Double admdIgstperc ;

	@Column(name="ADMD_SGST")
	private Double admdSgst ;

	@Column(name="ADMD_SGSTPERC")
	private Double admdSgstperc ;

	@Column(name="ADMD_SITE")
	private String admdSite ;

	@Column(name="ADMD_TDS")
	private Double admdTds ;

	@Column(name="ADMD_TDSPERC")
	private Double admdTdsperc ;

	@Column(name="ADMD_TODAY")
	private LocalDateTime admdToday ;

	@Column(name="ADMD_UGST")
	private Double admdUgst ;

	@Column(name="ADMD_UGSTPERC")
	private Double admdUgstperc ;

	@Column(name="ADMD_USERID")
	private String admdUserid ;

	@Column(name="ADMD_WORKCODE")
	private String admdWorkcode ;

}