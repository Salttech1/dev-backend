package kraheja.enggsys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the CERTDETAILS database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certdetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CertdetailsCK certdetailsCK;

	@Column(name="CRTD_ADVADJUSTED")
	private Double crtdAdvadjusted ;

	@Column(name="CRTD_BILLAMOUNT")
	private Double crtdBillamount ;

	@Column(name="CRTD_ORIGSITE")
	private String crtdOrigsite ;

	@Column(name="CRTD_RELRETAMT")
	private Double crtdRelretamt ;

	@Column(name="CRTD_SITE")
	private String crtdSite ;

	@Column(name="CRTD_TDSAMT")
	private Double crtdTdsamt ;

	@Column(name="CRTD_TODAY")
	private LocalDateTime crtdToday ;

	@Column(name="CRTD_USERID")
	private String crtdUserid ;
	
	@Embeddable
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CertdetailsCK implements Serializable{

		private static final long serialVersionUID = 1L;

		@Type(type = "kraheja.commons.utils.CharType") 
		private String crtdCertnum ; 

		@Type(type = "kraheja.commons.utils.CharType") 
		private String crtdContract ; 

		@Type(type = "kraheja.commons.utils.CharType") 
		private String crtdContbillno ; 
	}

}