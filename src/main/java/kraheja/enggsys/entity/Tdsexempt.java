package kraheja.enggsys.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
 * The persistent class for the TDSEXEMPT database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "TDSX_X", columnList = "tdsxContract Asc", unique = true)
})
public class Tdsexempt implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TdsexemptCK tdsexemptCK;

	@Column(name="TDSX_EXMPREF")
	private String tdsxExmpref ;

	@Column(name="TDSX_ORIGSITE")
	private String tdsxOrigsite ;

	@Column(name="TDSX_SITE")
	private String tdsxSite ;

	@Column(name="TDSX_TDSPER")
	private Integer tdsxTdsper ;

	@Column(name="TDSX_TDSUPTO")
	private LocalDate tdsxTdsupto ;
	
	@Column(name="TDSX_TDSFROM")
	private LocalDate tdsxTdsfrom ; 

	@Column(name="TDSX_TODAY")
	private LocalDateTime tdsxToday ;

	@Column(name="TDSX_USERID")
	private String tdsxUserid ;
	
	@Embeddable
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class TdsexemptCK implements Serializable{

		private static final long serialVersionUID = 1L;

		@Type(type = "kraheja.commons.utils.CharType") 
		private String tdsxContract ; 
		
	}
}