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
 * The persistent class for the BLDGWINGMAP database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(indexes = {
		@Index(name = "BLDGWINGMAP1", columnList = "bwmapBldgcode ASC, bwmapBldgwing ASC", unique = true)
})

public class Bldgwingmap implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BldgwingmapCK bldgwingmapCK;

	@Column(name="BWMAP_ALTBLDGCODE")
	private String bwmapAltbldgcode;

	@Column(name="BWMAP_ALTWING")
	private String bwmapAltwing;

	@Column(name="BWMAP_INFRABLDGCODE")
	private String bwmapInfrabldgcode;

	@Column(name="BWMAP_INFRAWING")
	private String bwmapInfrawing;

	@Column(name="BWMAP_MAINTBLDGCODE")
	private String bwmapMaintbldgcode;

	@Column(name="BWMAP_MAINTWING")
	private String bwmapMaintwing;

	@Column(name="BWMAP_ORIGSITE")
	private String bwmapOrigsite;

	@Column(name="BWMAP_SITE")
	private String bwmapSite;

	@Column(name="BWMAP_TODAY")
	private LocalDateTime bwmapToday;

	@Column(name="BWMAP_USERID")
	private String bwmapUserid;

}