package kraheja.adminexp.billing.dataentry.adminBill.entity;

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
* The persistent class for the REFER database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "REF1", columnList = "refReftype Asc, refRefcode Asc", unique = true)
})

public class Refer implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ReferCK referCK;

	@Column(name="REF_ACMAJOR")
	private String refAcmajor ;

	@Column(name="REF_REFDESC")
	private String refRefdesc ;

	@Column(name="REF_SITE")
	private String refSite ;

	@Column(name="REF_TODAY")
	private LocalDateTime refToday ;

	@Column(name="REF_USERID")
	private String refUserid ;

}