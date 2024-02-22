package kraheja.purch.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
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
 * The persistent class for the BLDGMATBILLFINAL database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "BLDGMATBILLFINAL", columnList = "bmbfBldgcode Asc, bmbfMgrcode Asc", unique = true)
})

public class Bldgmatbillfinal implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BldgmatbillfinalCK bldgmatbillfinalCK;

	@Column(name="BMBF_BILLFINALDATE")
	private LocalDate bmbfBillfinaldate ;

	@Column(name="BMBF_SITE")
	private String bmbfSite ;

	@Column(name="BMBF_TODAY")
	private LocalDateTime bmbfToday ;

	@Column(name="BMBF_USERID")
	private String bmbfUserid ;

}