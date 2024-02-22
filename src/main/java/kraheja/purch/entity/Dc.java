package kraheja.purch.entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
 * The persistent class for the DC database table.
 * 
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
		@Index(name = "DC1", columnList = "dcpEntryno Asc, dcpSuppcode Asc, dcpDcno Asc", unique = true)
})
public class Dc implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private DcCK dcCK;

	@Column(name="DCP_BILLDT")
	private LocalDate dcpBilldt;

	@Column(name="DCP_DCDATE")
	private LocalDate dcpDcdate;

	@Column(name="DCP_ORIGSITE")
	private String dcpOrigsite;

	@Column(name="DCP_PUKKA")
	private Character dcpPukka;

	@Column(name="DCP_SITE")
	private String dcpSite;

	@Column(name="DCP_SUPPBILL")
	@Type(type = "kraheja.commons.utils.CharType")
	private String dcpSuppbill;

	@Column(name="DCP_TODAY")
	private LocalDateTime dcpToday;

	@Column(name="DCP_USERID")
	private String dcpUserid;
}