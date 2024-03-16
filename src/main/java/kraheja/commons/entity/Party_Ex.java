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
* The persistent class for the PARTY_EX database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "PAR2", columnList = "pexPartycode Asc, pexPartytype Asc", unique = true)
})

public class Party_Ex implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Party_ExCK party_exCK;

	@Column(name="PEX_CONTACTPERSON")
	private String pexContactperson ;

	@Column(name="PEX_IPADDRESS")
	private String pexIpaddress ;

	@Column(name="PEX_MACHINENAME")
	private String pexMachinename ;

	@Column(name="PEX_MODIFIEDON")
	private LocalDateTime pexModifiedon ;

	@Column(name="PEX_MODULE")
	private String pexModule ;

	@Column(name="PEX_SITE")
	private String pexSite ;

	@Column(name="PEX_USERID")
	private String pexUserid ;

}
