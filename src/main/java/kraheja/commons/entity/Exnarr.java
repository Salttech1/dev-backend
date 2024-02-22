package kraheja.commons.entity;

import java.io.Serializable;
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
 * The persistent class for the EXNARR database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
		@Index(name = "EXN1", columnList = "exnTranser ASC, exnBunum ASC, exnNarrtype ASC, exnLinenum ASC, exnCoy ASC", unique = true),
})
public class Exnarr implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ExnarrCK exnarrCK;

	@Column(name="EXN_EXNARR")
    @Type(type = "kraheja.commons.utils.CharType")
	private String exnExnarr;

	@Column(name="EXN_OLDBUNUM")
	private Double exnOldbunum;

	@Column(name="EXN_SITE")
    @Type(type = "kraheja.commons.utils.CharType")
	private String exnSite;

	@Column(name="EXN_TODAY")
	private LocalDateTime exnToday;

	@Column(name="EXN_USERID")
    @Type(type = "kraheja.commons.utils.CharType")
	private String exnUserid;
}