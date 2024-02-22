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
 * The persistent class for the PROPRIETOR database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "PRIET1", columnList = "propCode Asc", unique = true)
})

public class Proprietor implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProprietorCK proprietorCK;

	@Column(name="PROP_ACTIVE")
	private String propActive ;

	@Column(name="PROP_CITY")
	private String propCity ;

	@Column(name="PROP_LASTYRCOMP")
	private Double propLastyrcomp ;

	@Column(name="PROP_LASTYRINC")
	private Double propLastyrinc ;

	@Column(name="PROP_LASTYRMAT")
	private Double propLastyrmat ;

	@Column(name="PROP_LASTYRWORK")
	private Double propLastyrwork ;

	@Column(name="PROP_NAME")
	private String propName ;

	@Column(name="PROP_NAMECHGDT")
	private LocalDate propNamechgdt ;

	@Column(name="PROP_OLDNAME")
	private String propOldname ;

	@Column(name="PROP_ORIGSITE")
	private String propOrigsite ;

	@Column(name="PROP_SITE")
	private String propSite ;

	@Column(name="PROP_THISYRCOMP")
	private Double propThisyrcomp ;

	@Column(name="PROP_THISYRINC")
	private Double propThisyrinc ;

	@Column(name="PROP_THISYRMAT")
	private Double propThisyrmat ;

	@Column(name="PROP_THISYRWORK")
	private Double propThisyrwork ;

	@Column(name="PROP_TODAY")
	private LocalDateTime propToday ;

	@Column(name="PROP_USERID")
	private String propUserid ;

}