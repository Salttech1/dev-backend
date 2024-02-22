package kraheja.purch.entity;

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
 * The persistent class for the AUTHMATGROUPNARRDTL database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "AUTHMATGROUPNARRDTL", columnList = "amndAuthnum Asc, amndSrno Asc", unique = true)
})

public class Authmatgroupnarrdtl implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AuthmatgroupnarrdtlCK authmatgroupnarrdtlCK;

	@Column(name="AMND_AMOUNT")
	private Double amndAmount ;

	@Column(name="AMND_ITEMDESC")
	private String amndItemdesc ;

	@Column(name="AMND_MATGROUP")
	private String amndMatgroup ;

	@Column(name="AMND_QUANTITY")
	private Double amndQuantity ;

	@Column(name="AMND_SITE")
	private String amndSite ;

	@Column(name="AMND_TODAY")
	private LocalDateTime amndToday ;

	@Column(name="AMND_USERID")
	private String amndUserid ;

}