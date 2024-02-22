package kraheja.commons.entity;

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
 * The persistent class for the MINORS database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "MIN1", columnList = "minMinorscode Asc, minMinorstype Asc, minClosedate Asc", unique = true)
})

public class Minors implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MinorsCK minorsCK;

	@Column(name="MIN_MINORSNAME")
	private String minMinorsname ;

	@Column(name="MIN_OPENDATE")
	private LocalDate minOpendate ;

	@Column(name="MIN_SITE")
	private String minSite ;

	@Column(name="MIN_TODAY")
	private LocalDateTime minToday ;

	@Column(name="MIN_USERID")
	private String minUserid ;

	@Column(name="MIN_VALIDMINOR")
	private String minValidminor ;

}