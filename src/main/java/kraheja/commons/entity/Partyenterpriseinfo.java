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
* The persistent class for the PARTYENTERPRISEINFO database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "PARTYENTERPRISEINFO", columnList = "peiPartycode Asc, peiPartytype Asc, peiEntcode Asc, peiEnttype Asc, peiEnddate Asc", unique = true)
})

public class Partyenterpriseinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PartyenterpriseinfoCK partyenterpriseinfoCK;

	@Column(name="PEI_COMMSTARTDATE")
	private LocalDate peiCommstartdate ;

	@Column(name="PEI_CONTACTPERSON")
	private String peiContactperson ;

	@Column(name="PEI_IPADDRESS")
	private String peiIpaddress ;

	@Column(name="PEI_MACHINENAME")
	private String peiMachinename ;

	@Column(name="PEI_MODIFIEDON")
	private LocalDateTime peiModifiedon ;

	@Column(name="PEI_MODULE")
	private String peiModule ;

	@Column(name="PEI_SITE")
	private String peiSite ;

	@Column(name="PEI_UANNO")
	private String peiUanno ;

	@Column(name="PEI_USERID")
	private String peiUserid ;

}
