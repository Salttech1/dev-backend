package kraheja.adminexp.overheads.dataentry.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

//@Table(indexes = {
//		@Index(name = "OHME1", columnList = "ohmeConnocode Asc, ohmeMeterno Asc", unique = true)
//})
public class Overheadmeter implements Serializable {
	
	@EmbeddedId
	private OverheadmeterCK overheadmeterCK;

	@Column(name="OHME_SITE")
	private String site ;

	@Column(name="OHME_TODAY")
	private LocalDateTime today ;

	@Column(name="OHME_USERID")
	private String userid ;

}
