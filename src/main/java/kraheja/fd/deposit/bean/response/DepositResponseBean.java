package kraheja.fd.deposit.bean.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.response.InchqResponseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class DepositResponseBean {
	
	private Double accint;
	
	private String bankcode;
	
	private String brokcheq;
	
	private LocalDateTime brokcheqdate;
	
	private String broker;
	
	private Double brokerage;
	
	private Double brokper;
	
	private Double broktds;
	
	private LocalDateTime canceldate;
	
	private String coy;

	private Float depamount;
	
	private LocalDateTime depdate;
	
	private Double depmonths;
	
	private String deporigin;
	
	private String depositor;
	
	private LocalDateTime disdate;
	
	private String expirystatus;
	
	private String extendedyn;
	
	private Double grossint;
	
	private String instbp;
	
	private String instfdr;
	
	private String instip;
	
	private String instructions;
	
	private String intaccyymm;
	
	private Double intfreq;
	
	private Double intpaidytd;
	
	private Double intrate;
	
	private Double intrate2;
	
	private Double intrateold;
	
	private String jowner1;
	
	private String jowner2;
	
	private String jowner3;
	
	private String liqtype;
	
	private Double matamount;
	
	private LocalDateTime matdate;
	
	private String ngid1;
	
	private String ngid2;
	
	private String ngid3;
	
	private String ngname1;
	
	private String ngname2;
	
	private String ngname3;
	
	private String nominee;
	
	private Double origrate;
	
	private String origreceipt;
	
	private String origsite;
	
	private Double payamount;
	
	private LocalDateTime paydate;
	
	private String payeecode;
	
	private String payref;
	
	private LocalDateTime prevmaturitydate;
	
	private LocalDateTime printedon;
	
	private Double printrev;
	
	private String proprietor;
	
	private LocalDateTime receiptdate;
	
	private String receiptnum;
	
	private String site;
	
	private String staffyn;
	
	private Double taxpaidytd;
	
	private Double tds;
	
	private LocalDateTime today;
	
	private LocalDateTime transfer;
	
	private String userid;
	
	private String waracc;
	
	private String warbank;
	
	private Object age;
	
	private Double rateOfInterest;
	
	private String birthDate;
	
	private String depositorName;
	
	private Integer ddCharges;
	
	private Integer interest;
	
	private Boolean isChecked;
	
	private List<InchqResponseBean> inchqResponseBean;
}
