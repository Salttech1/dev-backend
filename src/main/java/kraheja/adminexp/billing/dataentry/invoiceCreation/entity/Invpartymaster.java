package kraheja.adminexp.billing.dataentry.invoiceCreation.entity;
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
 * The persistent class for the INVPARTYMASTER database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "INVPARTYMASTER1", columnList = "ipmsCoycode Asc, ipmsPartytype Asc, ipmsPartycode Asc, ipmsBilltype Asc, ipmsItemBillCode Asc", unique = true)
})

public class Invpartymaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InvpartymasterCK invpartymasterCK;

	@Column(name="IPMS_ACMAJOR")
	private String ipmsAcmajor ;

	@Column(name="IPMS_BILLNOTE")
	private String ipmsBillnote ;

	@Column(name="IPMS_BILLPROCESSTYPE")
	private String ipmsBillprocesstype ;

	@Column(name="IPMS_EMAILID")
	private String ipmsEmailid ;

	@Column(name="IPMS_HSNSAC")
	private String ipmsHsnsac ;

	@Column(name="IPMS_ITEM_BILL_DESC")
	private String ipmsItemBillDesc ;

	@Column(name="IPMS_NARRATION")
	private String ipmsNarration ;

	@Column(name="IPMS_QTY")
	private Integer ipmsQty ;

	@Column(name="IPMS_RATE")
	private Double ipmsRate ;

	@Column(name="IPMS_SERVICENATURE")
	private String ipmsServicenature ;

	@Column(name="IPMS_SIGNAUTH")
	private String ipmsSignauth ;

	@Column(name="IPMS_SITE")
	private String ipmsSite ;

	@Column(name="IPMS_SUBJECT")
	private String ipmsSubject ;

	@Column(name="IPMS_TODAY")
	private LocalDateTime ipmsToday ;

	@Column(name="IPMS_USER")
	private String ipmsUser ;

}
