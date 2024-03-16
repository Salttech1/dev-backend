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
 * The persistent class for the TEMP_CERTPRINT database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "TEMP_CERTPRINT", columnList = "tmpCertnum Asc, tmpType Asc, tmpCode Asc, tmpSessionid Asc", unique = true)
})

public class TempCertprint implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TempCertprintCK tempcertprintCK;

	@Column(name="TMP_CODEDESC")
	private String tmpCodedesc ;

	@Column(name="TMP_GROUPCODE")
	private String tmpGroupcode ;

	@Column(name="TMP_GROUPDESC")
	private String tmpGroupdesc ;

	@Column(name="TMP_SITE")
	private String tmpSite ;

	@Column(name="TMP_TODAY")
	private LocalDateTime tmpToday ;

	@Column(name="TMP_TOTAMT")
	private Integer tmpTotamt ;

	@Column(name="TMP_USERID")
	private String tmpUserid ;

}