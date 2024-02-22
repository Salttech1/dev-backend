package kraheja.adminexp.insurance.dataentry.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
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
 * The persistent class for the INSASSETITEMINSURED database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "INSASSETITEMINSURED1", columnList = "iaiPolicyid Asc, iaiLinenumber Asc", unique = true)
})

public class Insassetiteminsured implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InsassetiteminsuredCK insassetiteminsuredCK;

	@Column(name="IAI_BRAND")
	private String iaiBrand ;
	
	@Column(name="IAI_POLICYNUMBER")
	private String iaiPolicynumber;

	@Column(name="IAI_EPCGVALUE")
	private Double iaiEpcgvalue ;
	
	@Type(type = "kraheja.commons.utils.CharType") 
	@Column(name="IAI_ITEMSERIAL")
	private String iaiitemserial ;
	
	@Column(name="IAI_BLDGCODE")
	private String iaiBldgcode ;
	
	@Type(type = "kraheja.commons.utils.CharType") 
	@Column(name="IAI_GROUPCODE")
	private String iaiGroupcode ;
	
	@Type(type = "kraheja.commons.utils.CharType") 
	@Column(name="IAI_SRNO")
	private String iaisrno ;

	@Type(type = "kraheja.commons.utils.CharType") 
	@Column(name="IAI_GROUPNAME")
	private String iaiGroupname ;

	@Column(name="IAI_IPADDRESS")
	private String iaiIpaddress ;

	@Column(name="IAI_ITEM")
	private String iaiItem ;

	@Column(name="IAI_ITEMDESC")
	private String iaiItemdesc ;

	@Column(name="IAI_MACHINENAME")
	private String iaiMachinename ;

	@Column(name="IAI_MODIFIEDON")
	private LocalDateTime iaiModifiedon ;

	@Column(name="IAI_MODULE")
	private String iaiModule ;

	@Column(name="IAI_PURCHASEDATE")
	private LocalDate iaiPurchasedate ;

	@Type(type = "kraheja.commons.utils.CharType") 
	@Column(name="IAI_PURCHASEYEAR")
	private String iaiPurchaseyear ;

	@Column(name="IAI_QTY")
	private Double iaiQty ;

	@Column(name="IAI_QTYDESC")
	private String iaiQtydesc ;

	@Column(name="IAI_REMARK")
	private String iaiRemark ;

	@Column(name="IAI_REVISEDVALUE")
	private Double iaiRevisedvalue ;

	@Column(name="IAI_SITE")
	private String iaiSite ;

	@Type(type = "kraheja.commons.utils.CharType") 
	@Column(name="IAI_SUBGROUPCODE")
	private String iaiSubgroupcode ;

	@Type(type = "kraheja.commons.utils.CharType") 
	@Column(name="IAI_SUBGROUPNAME")
	private String iaiSubgroupname ;

	@Column(name="IAI_USERID")
	private String iaiUserid ;

	@Column(name="IAI_VALUE")
	private Double iaiValue ;

	@Column(name="IAI_VALUEWITHDUTY")
	private Double iaiValuewithduty ;

}