package kraheja.enggsys.entity;

import java.io.Serializable;
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
 * The persistent class for the MATCERTLNVENDORHDR database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "MATCERTLNVENDORHDR1", columnList = "mcvhLogicnotenum Asc, mcvhVendorsrno Asc", unique = true)
})

public class Matcertlnvendorhdr implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MatcertlnvendorhdrCK matcertlnvendorhdrCK;

	@Column(name="MCVH_BGRECEIVEDATE")
	private LocalDate mcvhBgreceivedate ;

	@Column(name="MCVH_BRAND")
	private String mcvhBrand ;

	@Column(name="MCVH_CURRENCYAMT")
	private Integer mcvhCurrencyamt ;

	@Column(name="MCVH_CURRENCYRATE")
	private Integer mcvhCurrencyrate ;

	@Column(name="MCVH_CURRENCYTYPE")
	private String mcvhCurrencytype ;

	@Column(name="MCVH_DELIVERYDATE")
	private LocalDate mcvhDeliverydate ;

	@Column(name="MCVH_DELIVERYWEEKS")
	private String mcvhDeliveryweeks ;

	@Column(name="MCVH_GROUPLNNUM")
	private String mcvhGrouplnnum ;

	@Column(name="MCVH_ORDERDATE")
	private LocalDate mcvhOrderdate ;

	@Column(name="MCVH_PARTYCODE")
	private String mcvhPartycode ;

	@Column(name="MCVH_PARTYTYPE")
	private String mcvhPartytype ;

	@Column(name="MCVH_QUOTEDATE")
	private LocalDate mcvhQuotedate ;

	@Column(name="MCVH_SITE")
	private String mcvhSite ;

	@Column(name="MCVH_TODAY")
	private LocalDateTime mcvhToday ;

	@Column(name="MCVH_USERID")
	private String mcvhUserid ;

	@Column(name="MCVH_VENDORCODE")
	private String mcvhVendorcode ;

	@Column(name="MCVH_VENDORNAME")
	private String mcvhVendorname ;

	@Column(name="MCVH_VENDORORDERNO")
	private String mcvhVendororderno ;

	@Column(name="MCVH_VENDORSELECTEDYN")
	private String mcvhVendorselectedyn ;

}