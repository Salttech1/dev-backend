package kraheja.enggsys.entity;

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
 * The persistent class for the MATCERTLNTENDERCOMMITTEE database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "MATCERTLNTENDERCOMMITTEE1", columnList = "mclcEntrynum Asc, mclcPersoncode Asc", unique = true)
})

public class Matcertlntendercommittee implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MatcertlntendercommitteeCK matcertlntendercommitteeCK;

	@Column(name="MCLC_SITE")
	private String mclcSite ;

	@Column(name="MCLC_SRNO")
	private String mclcSrno ;

	@Column(name="MCLC_TODAY")
	private LocalDateTime mclcToday ;

	@Column(name="MCLC_USERID")
	private String mclcUserid ;

}