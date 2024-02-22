package kraheja.adminexp.overheads.dataentry.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the LOCATION database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LOC_CODE")
	@Type(type = "kraheja.commons.utils.CharType")
	private String code ;

	@Column(name="LOC_BLDGCODE")
	private String locBldgcode ;

	@Column(name="LOC_DUEFROMSOCIETY")
	private String locDuefromsociety ;

	@Column(name="LOC_NAME")
	private String locName ;

	@Column(name="LOC_SITE")
	private String locSite ;

	@Column(name="LOC_TODAY")
	private LocalDateTime locToday ;

	@Column(name="LOC_USERID")
	private String locUserid ;

}
