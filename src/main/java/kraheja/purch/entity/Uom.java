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
 * The persistent class for the UOM database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "UOM1", columnList = "uomMatgroup Asc, uomCode Asc", unique = true)
})

public class Uom implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UomCK uomCK;

	@Column(name="UOM_NAME")
	private String uomName ;

	@Column(name="UOM_ORIGSITE")
	private String uomOrigsite ;

	@Column(name="UOM_SITE")
	private String uomSite ;

	@Column(name="UOM_TODAY")
	private LocalDateTime uomToday ;

	@Column(name="UOM_USERID")
	private String uomUserid ;

}