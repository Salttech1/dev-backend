package kraheja.adminexp.insurance.dataentry.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
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
 * The persistent class for the INSPREMPAYSCH database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "INSPREMPAYSCH1", columnList = "ippsPolicyid Asc, ippsPolicynumber Asc, ippsLinenumber Asc", unique = true)
})

public class Insprempaysch implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InsprempayschCK insprempayschCK;

	@Type(type = "kraheja.commons.utils.CharType")    //Added by Shahaji 11/05/2023
	@Column(name="IPPS_CERTNUM")
	private String ippsCertnum ;

	@Column(name="IPPS_IPADDRESS")
	private String ippsIpaddress ;

	@Column(name="IPPS_MACHINENAME")
	private String ippsMachinename ;

	@Column(name="IPPS_MODIFIEDON")
	private LocalDateTime ippsModifiedon ;

	@Column(name="IPPS_MODULE")
	private String ippsModule ;

	@Column(name="IPPS_PAYAMT")
	private Double ippsPayamt ;

	@Column(name="IPPS_PAYDATE")
	private LocalDate ippsPaydate ;

	@Column(name="IPPS_REMARK")
	private String ippsRemark ;

	@Column(name="IPPS_SITE")
	private String ippsSite ;

	@Column(name="IPPS_STATUS")
	private String ippsStatus ;

	@Column(name="IPPS_TRANSER")
	private String ippsTranser ;

	@Column(name="IPPS_USERID")
	private String ippsUserid ;

}