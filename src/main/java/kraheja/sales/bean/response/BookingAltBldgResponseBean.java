package kraheja.sales.bean.response;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.response.AddressResponseBean;
import kraheja.commons.bean.response.PartyResponseBean;
import kraheja.sales.entity.Loanhistory;
import kraheja.sales.repository.FlatsRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class BookingAltBldgResponseBean {
	
	private String aadharno ;
	private String accomtype ;
	private Double agprice ;
	private Double amtos ;
	private Double amtrec ;
	private String area ;
	private String bldgcode ;
	private String bookedby ;
	private Double brokent ;
	private String broker ;
	private Double brokos ;
	private Double brokpaid ;
	private Double broktdsamd ;
	private Double broktdsamt ;
	private Double broktdsper ;
	private String broktranser ;
	private String cancelled ;
	private String community ;
	private String contracton ;
	private String customercoy ;
	private String custtype ;
	private String date ;
	private String designation ;
	private Double discount ;
	private String firstvisitdate ;
	private String firstvisitexec ;
	private String flatnum ;
	private String floor ;
	private String gstno ;
	private String ho2owner ;
	private String jobprofile ;
	private String leaddate ;
	private String leasedto ;
	private String leaseref ;
	private String ledby ;
	private Double maintrate ;
	private String mpaiddate ;
	private String mpaidref ;
	private String mpaidyymm ;
	private String origsite ;
	private String overon ;
	private String ownerid ;
	private String panum ;
	private String poacode ;
	private String poaname ;
	private Integer regfees ;
	private String regno ;
	private Integer regprice ;
	private String remarks ;
	private String salestatus ;
	private String saletype ;
	private String scheduledpossession ;
	private String serialnum ;
	private String site ;
	private String soi ;
	private LocalDateTime today ;
	private String userid ;
	private String validtill ;
	private String wing ;
	private String AlternetBldgcode;
	private String AlternentOwnerid;
	
	PartyResponseBean partyAltBldgResponseBean;
	private AddressResponseBean addresspmtAltBldgResponseBean;
	private AddressResponseBean addressmailAltBldgResponseBean;
	
	private List<FlatownerResponseBean> flatownerAltBldgResponseBean;
	private List<FlatpayResponseBean> flatpayAltBldgResponseBean;
	private List<FlatcharResponseBean> flatcharAltBldgResponseBean;
	private List<LoanhistoryResponseBean> loanhistoryAltBldgResponseBean;
	
}
