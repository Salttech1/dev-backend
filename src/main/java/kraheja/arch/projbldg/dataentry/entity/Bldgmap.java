package kraheja.arch.projbldg.dataentry.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
 * The persistent class for the BLDGMAP database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "BLDGMAP", columnList = "bmapEbldgcode Asc, bmapAbldgcode Asc", unique = true)
})

public class Bldgmap implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BldgmapCK bldgmapCK;

	@Column(name="BMAP_ABLDGNAME")
	private String bmapAbldgname ;

	@Column(name="BMAP_BLOCKCERTTYPE")
	private String bmapBlockcerttype ;

	@Column(name="BMAP_CLOSEDATE")
	private LocalDate bmapClosedate ;

	@Column(name="BMAP_COY1")
	private String bmapCoy1 ;

	@Column(name="BMAP_COY2")
	private String bmapCoy2 ;

	@Column(name="BMAP_COY3")
	private String bmapCoy3 ;

	@Column(name="BMAP_EBLDGNAME")
	private String bmapEbldgname ;

	@Column(name="BMAP_ORIGSITE")
	private String bmapOrigsite ;

	@Column(name="BMAP_SITE")
	private String bmapSite ;

	@Column(name="BMAP_TODAY")
	private LocalDateTime bmapToday ;

	@Column(name="BMAP_USERID")
	private String bmapUserid ;

}