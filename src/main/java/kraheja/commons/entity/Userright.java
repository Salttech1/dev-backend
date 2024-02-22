package kraheja.commons.entity;

import java.io.Serializable;
import java.util.Date;

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
 * The persistent class for the USERRIGHTS database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor@Table(name="USERRIGHTS", indexes = {
		@Index(name = "USERRIGHTS1", columnList = "usrtStaffcode ASC, usrtModulerightcode ASC", unique = true),
})
public class Userright implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private UserrightCK userrightCK;

	@Column(name="USRT_DROPICONSYN")
	private String usrtDropiconsyn;

	@Column(name="USRT_ISSTAFF")
	private String usrtIsstaff;

	@Column(name="USRT_SITE")
	private String usrtSite;

	@Column(name="USRT_TODAY")
	private Date usrtToday;

	@Column(name="USRT_USERID")
	private String usrtUserid;

}