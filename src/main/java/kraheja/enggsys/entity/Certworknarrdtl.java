package kraheja.enggsys.entity;

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
 * The persistent class for the CERTWORKNARRDTL database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certworknarrdtl implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CertworknarrdtlCK certworknarrdtlCK;

	@Column(name="CWND_AMOUNT")
	private Integer cwndAmount ;

	@Column(name="CWND_ITEMDESC")
	private String cwndItemdesc ;

	@Column(name="CWND_QUANTITY")
	private Double cwndQuantity ;

	@Column(name="CWND_SITE")
	private String cwndSite ;

	@Column(name="CWND_TODAY")
	private LocalDateTime cwndToday ;

	@Column(name="CWND_USERID")
	private String cwndUserid ;

	@Column(name="CWND_WORKCODE")
	private String cwndWorkcode ;
	
	@Embeddable
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor

	public static class CertworknarrdtlCK implements Serializable{

		private static final long serialVersionUID = 1L;

		@Type(type = "kraheja.commons.utils.CharType") 
		private String cwndCertnum ; 
		 
		private Integer cwndSrno ; 
	}

}