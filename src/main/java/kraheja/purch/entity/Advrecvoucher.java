package kraheja.purch.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
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
 * The persistent class for the ADVRECVOUCHER database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "ADVRECVOUCHER1", columnList = "adrvOrigdocnum Asc, adrvLineno Asc", unique = true), 
		@Index(name = "ADVRECVOUCHER2", columnList = "adrvCoy Asc, adrvBldgcode Asc, adrvMatcertcode Asc, adrvPartytype Asc, adrvPartycode Asc, adrvOrigsys Asc, adrvOrigdocnum Asc, adrvLineno Asc", unique = true)
})

public class Advrecvoucher implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdvrecvoucherCK advrecvoucherCK;
	
	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String adrvCoy ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String adrvBldgcode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String adrvMatcertcode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String adrvPartytype ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String adrvPartycode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String adrvOrigsys ; 

	@Column(name="ADRV_AMOUNT")
	private Double adrvAmount ;

	@Column(name="ADRV_CGSTAMT")
	private Double adrvCgstamt ;

	@Column(name="ADRV_CGSTPERC")
	private Double adrvCgstperc ;

	@Column(name="ADRV_HSNSACCODE")
	private String adrvHsnsaccode ;

	@Column(name="ADRV_IGSTAMT")
	private Double adrvIgstamt ;

	@Column(name="ADRV_IGSTPERC")
	private Double adrvIgstperc ;

	@Column(name="ADRV_ITEMDESC")
	private String adrvItemdesc ;

	@Column(name="ADRV_ORIGDOCDATE")
	private LocalDate adrvOrigdocdate ;

	@Column(name="ADRV_ORIGSITE")
	private String adrvOrigsite ;

	@Column(name="ADRV_PROFINVDT")
	private LocalDate adrvProfinvdt ;

	@Column(name="ADRV_PROFINVNO")
	private String adrvProfinvno ;

	@Column(name="ADRV_RECVOUDT")
	private LocalDate adrvRecvoudt ;

	@Column(name="ADRV_RECVOUNUM")
	private String adrvRecvounum ;

	@Column(name="ADRV_SGSTAMT")
	private Double adrvSgstamt ;

	@Column(name="ADRV_SGSTPERC")
	private Double adrvSgstperc ;

	@Column(name="ADRV_SITE")
	private String adrvSite ;

	@Column(name="ADRV_TODAY")
	private LocalDateTime adrvToday ;

	@Column(name="ADRV_UGSTAMT")
	private Double adrvUgstamt ;

	@Column(name="ADRV_UGSTPERC")
	private Double adrvUgstperc ;

	@Column(name="ADRV_USERID")
	private String adrvUserid ;

}