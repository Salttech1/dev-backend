package kraheja.fd.deposit.bean.request;

import java.util.Date;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.annotaions.PanNumberCheck;
import kraheja.commons.bean.request.AddressRequestBean;
import kraheja.commons.bean.request.PartyRequestBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class DepositorRequestBean {
	
	private String depositorId;
	
	private String companyCode;
	
	private	String depositor;
	
	private String title;
	
	private String name;
	
	private String proprietor;
	
	private String coy;
	
	private Double depamount;
	
	private Double grossint;
	
	private Double accint;
	
	private Double tds;
	
	private String remarks;
	
	private String city;
	
	private String site;
	
	private String userid;
	
	private String today;
	
	private String origsite;
	
	private String deptype;
//	
	private String taxyn;
	
	private String taxalwaysyn;
	
	private String tds15hyn;
	
	private String tds15gyn;
	
	private Double intpayedytd;
	
	private Double taxpaidytd;
	
	private Double taxrecytd;
	
	private String clubref;
	
	@PanNumberCheck
	private String pannum1;

	@PanNumberCheck
	private String pannum2;
	
	private String insupd;
	
	private Date birthdate;
	
	private Boolean isTransferSeries;
	
	private Date fifteenhrecd;
	
	private Date fifteenhsubd;
	
	private AddressRequestBean addressRequestBean;
	
	@Valid
	private PartyRequestBean partyRequestBean;
}
