package kraheja.fd.deposit.bean.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.response.AddressResponseBean;
import kraheja.commons.bean.response.PartyResponseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class DepositorResponseBean {
	
	private String coy;

	private String depositor;
	
	private Date fifteenHrecd;

	private Date fifteenHsubd;

	private Double accint;

	private Long birthdate;

	private String city;

	private String clubref;

	private Double depamount;

	private String deptype;

	private Double grossint;

	private Double intpaidytd;

	private String name;

	private String origsite;

	private String panum1;

	private String panum2;

	private String proprietor;

	private String remarks;

	private String site;

	private String taxalwaysyn;

	private Double taxpaidytd;

	private Double taxrecytd;

	private Double tds;

	private String tds15gyn;

	private String tds15hyn;

	private String title;

	private Date today;
	
	private Boolean isTransferSeries;

	private String userid;
	
	PartyResponseBean partyResponseBean;

	AddressResponseBean addressResponseBean;
}
