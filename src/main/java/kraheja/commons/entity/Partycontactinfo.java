package kraheja.commons.entity;

import java.io.Serializable;
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
* The persistent class for the PARTYCONTACTINFO database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "PARTYCONTACTINFO", columnList = "pciPartycode Asc", unique = true)
})

public class Partycontactinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PartycontactinfoCK partycontactinfoCK;

	@Column(name="PCI_BRAND")
	private String pciBrand ;

	@Column(name="PCI_COORD1PHONE")
	private String pciCoord1phone ;

	@Column(name="PCI_COORD2PHONE")
	private String pciCoord2phone ;

	@Column(name="PCI_COORDINATOR1")
	private String pciCoordinator1 ;

	@Column(name="PCI_COORDINATOR2")
	private String pciCoordinator2 ;

	@Column(name="PCI_HEADCONTPERSON")
	private String pciHeadcontperson ;

	@Column(name="PCI_HEADCONTPHONE")
	private String pciHeadcontphone ;

	@Column(name="PCI_IPADDRESS")
	private String pciIpaddress ;

	@Column(name="PCI_MACHINENAME")
	private String pciMachinename ;

	@Column(name="PCI_MODIFIEDON")
	private LocalDateTime pciModifiedon ;

	@Column(name="PCI_MODULE")
	private String pciModule ;

	@Column(name="PCI_REMARKS")
	private String pciRemarks ;

	@Column(name="PCI_SITE")
	private String pciSite ;

	@Column(name="PCI_USERID")
	private String pciUserid ;

}
