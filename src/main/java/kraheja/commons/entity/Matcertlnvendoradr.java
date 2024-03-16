package kraheja.commons.entity;

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
* The persistent class for the MATCERTLNVENDORADR database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "MATCERTLNVENDORADR1", columnList = "mcvaAdsegment Asc, mcvaAdowner Asc, mcvaAdtype Asc, mcvaAdser Asc", unique = true)
})

public class Matcertlnvendoradr implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MatcertlnvendoradrCK matcertlnvendoradrCK;

	@Column(name="MCVA_ADLINE1")
	private String mcvaAdline1 ;

	@Column(name="MCVA_ADLINE2")
	private String mcvaAdline2 ;

	@Column(name="MCVA_ADLINE3")
	private String mcvaAdline3 ;

	@Column(name="MCVA_ADLINE4")
	private String mcvaAdline4 ;

	@Column(name="MCVA_ADLINE5")
	private String mcvaAdline5 ;

	@Column(name="MCVA_ADTOPSER")
	private String mcvaAdtopser ;

	@Column(name="MCVA_BIRTHDAY")
	private LocalDate mcvaBirthday ;

	@Column(name="MCVA_CITY")
	private String mcvaCity ;

	@Column(name="MCVA_COORD1PHONE")
	private String mcvaCoord1phone ;

	@Column(name="MCVA_COORD2PHONE")
	private String mcvaCoord2phone ;

	@Column(name="MCVA_COORDINATOR1")
	private String mcvaCoordinator1 ;

	@Column(name="MCVA_COORDINATOR2")
	private String mcvaCoordinator2 ;

	@Column(name="MCVA_COUNTRY")
	private String mcvaCountry ;

	@Column(name="MCVA_EMAIL")
	private String mcvaEmail ;

	@Column(name="MCVA_FAX")
	private String mcvaFax ;

	@Column(name="MCVA_HEADCONTPERSON")
	private String mcvaHeadcontperson ;

	@Column(name="MCVA_HEADCONTPHONE")
	private String mcvaHeadcontphone ;

	@Column(name="MCVA_ORIGSITE")
	private String mcvaOrigsite ;

	@Column(name="MCVA_PARTYCODE")
	private String mcvaPartycode ;

	@Column(name="MCVA_PARTYTYPE")
	private String mcvaPartytype ;

	@Column(name="MCVA_PHONEMOBILE")
	private String mcvaPhonemobile ;

	@Column(name="MCVA_PHONEOFF")
	private String mcvaPhoneoff ;

	@Column(name="MCVA_PHONERES")
	private String mcvaPhoneres ;

	@Column(name="MCVA_PINCODE")
	private String mcvaPincode ;

	@Column(name="MCVA_SITE")
	private String mcvaSite ;

	@Column(name="MCVA_STATE")
	private String mcvaState ;

	@Column(name="MCVA_TODAY")
	private LocalDateTime mcvaToday ;

	@Column(name="MCVA_TOWNSHIP")
	private String mcvaTownship ;

	@Column(name="MCVA_USERID")
	private String mcvaUserid ;

}
