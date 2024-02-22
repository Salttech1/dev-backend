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
 * The persistent class for the HSNSACMASTER database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "HSNSACMASTER", columnList = "hsmsCode Asc, hsmsType Asc, hsmsOpendate Asc", unique = true)
})

public class Hsnsacmaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private HsnsacmasterCK hsnsacmasterCK;

	@Column(name="HSMS_CGSTPERC")
	private Double hsmsCgstperc ;

	@Column(name="HSMS_CLOSEDATE")
	private LocalDate hsmsClosedate ;

	@Column(name="HSMS_DESC")
	private String hsmsDesc ;

	@Column(name="HSMS_IGSTPERC")
	private Double hsmsIgstperc ;

	@Column(name="HSMS_ORIGSITE")
	private String hsmsOrigsite ;

	@Column(name="HSMS_PERC")
	private Double hsmsPerc ;

	@Column(name="HSMS_SGSTPERC")
	private Double hsmsSgstperc ;

	@Column(name="HSMS_SITE")
	private String hsmsSite ;

	@Column(name="HSMS_TODAY")
	private LocalDateTime hsmsToday ;

	@Column(name="HSMS_UGSTPERC")
	private Double hsmsUgstperc ;

	@Column(name="HSMS_USERID")
	private String hsmsUserid ;

}