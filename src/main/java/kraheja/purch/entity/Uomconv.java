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
 * The persistent class for the UOMCONV database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "UOMCONV1", columnList = "ucnvFrcode Asc, ucnvTocode Asc", unique = true)
})

public class Uomconv implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UomconvCK uomconvCK;

	@Column(name="UCNV_FACTOR")
	private Double ucnvFactor ;

	@Column(name="UCNV_ORIGSITE")
	private String ucnvOrigsite ;

	@Column(name="UCNV_SITE")
	private String ucnvSite ;

	@Column(name="UCNV_TODAY")
	private LocalDateTime ucnvToday ;

	@Column(name="UCNV_USERID")
	private String ucnvUserid ;

}