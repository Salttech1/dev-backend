package kraheja.sales.entity;

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
 * The persistent class for the FLATCHAR database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "FCH1", columnList = "fchBldgcode ASC, fchFlatnum ASC, fchAccomtype, fchChargecode ASC, fchWing ASC", unique = true)
})

public class Flatchar implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FlatcharCK flatcharCK;

	@Column(name="FCH_AMTDUE")
	private Double fchAmtdue;

	@Column(name="FCH_AMTPAID")
	private Double fchAmtpaid;

	@Column(name="FCH_ORIGSITE")
	private String fchOrigsite;

	@Column(name="FCH_SITE")
	private String fchSite;

	@Column(name="FCH_SQFTWISEYN")
	private String fchSqftwiseyn;

	@Column(name="FCH_TODAY")
	private LocalDateTime fchToday;

	@Column(name="FCH_USERID")
	private String fchUserid;

}