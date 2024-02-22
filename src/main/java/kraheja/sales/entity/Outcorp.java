package kraheja.sales.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the OUTCORP database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(indexes = {
		@Index(name = "OUTCORP_X", columnList = "outcBldgcode ASC, outcFlatnum ASC, outcWing ASC, outcFlats ASC", unique = true)
})
public class Outcorp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OutcorpCK outcorpCK;

	@Column(name="OUTC_RATE")
	private BigDecimal outcRate;

	@Column(name="OUTC_SITE")
	private String outcSite;

	@Column(name="OUTC_TODAY")
	private LocalDateTime outcToday;

	@Column(name="OUTC_USERID")
	private String outcUserid;

}