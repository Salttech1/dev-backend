// Developed By  - 	kalpana.m
// Developed on - 04-08-23
// Mode  - Data Entry
// Purpose - Detnarr Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.commons.entity;

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
 * The persistent class for the DETNARR database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "DNARR1", columnList = "detCoy Asc, detTranser Asc, detBunum Asc", unique = true)
})

public class Detnarr implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DetnarrCK detnarrCK;

	@Column(name="DET_DETTYPE")
	private String detDettype ;

	@Column(name="DET_NARRATIVE")
	private String detNarrative ;

	@Column(name="DET_SITE")
	private String detSite ;

	@Column(name="DET_TODAY")
	private LocalDateTime detToday ;

	@Column(name="DET_USERID")
	private String detUserid ;

}