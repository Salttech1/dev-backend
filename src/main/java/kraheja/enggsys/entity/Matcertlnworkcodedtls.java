package kraheja.enggsys.entity;

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
 * The persistent class for the MATCERTLNWORKCODEDTLS database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "MATCERTLNWORKCODEDTLS1", columnList = "mclwLogicnotenum Asc, mclwGroupcode Asc, mclwSubgroupcode Asc, mclwMatcerttype Asc, mclwMatcertcode Asc", unique = true)
})

public class Matcertlnworkcodedtls implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MatcertlnworkcodedtlsCK matcertlnworkcodedtlsCK;

	@Column(name="MCLW_AMOUNT")
	private Double mclwAmount ;

	@Column(name="MCLW_SITE")
	private String mclwSite ;

	@Column(name="MCLW_TODAY")
	private LocalDateTime mclwToday ;

	@Column(name="MCLW_USERID")
	private String mclwUserid ;

}