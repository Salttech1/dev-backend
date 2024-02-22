package kraheja.commons.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the COYMAP database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "COYMAP", columnList = "cmapEcoycode Asc, cmapAcoycode Asc", unique = true)
})

public class Coymap implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CoymapCK coymapCK;

	@Column(name="CMAP_ACOYNAME")
	private String cmapAcoyname ;

	@Column(name="CMAP_ECOYNAME")
	private String cmapEcoyname ;

	@Column(name="CMAP_ORIGSITE")
	private String cmapOrigsite ;

	@Column(name="CMAP_SITE")
	private String cmapSite ;

	@Column(name="CMAP_TODAY")
	private LocalDateTime cmapToday ;

	@Column(name="CMAP_USERID")
	private String cmapUserid ;
	
	@Embeddable
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor

	public static class CoymapCK implements Serializable{

		private static final long serialVersionUID = 1L;

		@Type(type = "kraheja.commons.utils.CharType") 
		private String cmapEcoycode ; 

		@Type(type = "kraheja.commons.utils.CharType") 
		private String cmapAcoycode ; 
	}

}