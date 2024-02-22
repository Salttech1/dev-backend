package kraheja.adminexp.billing.dataentry.invoiceCreation.entity;

import java.io.Serializable;
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
* The persistent class for the COYBILLSERIAL database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "COYBILLSERIAL", columnList = "cbsCoycode Asc, cbsChargecode Asc, cbsBilltype Asc, cbsSitename Asc, cbsYear Asc", unique = true)
})

public class Coybillserial implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CoybillserialCK coybillserialCK;

	@Column(name="CBS_MODIFIEDON")
	private LocalDateTime cbsModifiedon ;

	@Column(name="CBS_SITE")
	private String cbsSite ;

	@Column(name="CBS_SITECHAR")
	private String cbsSitechar ;

	@Column(name="CBS_SRNO")
	private Double cbsSrno ;

	@Column(name="CBS_USERID")
	private String cbsUserid ;

}