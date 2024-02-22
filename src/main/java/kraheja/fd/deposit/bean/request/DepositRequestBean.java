package kraheja.fd.deposit.bean.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.request.InchqRequestBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequestBean {
	
	private Double accint;
	
	private String bankcode;
	
	private String brokcheq;
	
	private String brokcheqdate;
	
	private String broker;
	
	private Double brokerage;
	
	private Double brokper;
	
	private Double broktds;
	
	private String canceldate;
	
	private String coy;

	private Float depamount;
	
	private String depdate;
	
	private String depdatenew;
	
	private Double depmonths;

	private Double depmonthnew;
	
	private String deporigin;
	
	private String depositor;
	
	private String disdate;
	
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
	
	private String matdate;
	
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
	
	private String paydate;
	
	private String payeecode;
	
	private String payref;
	
	private String prevmaturitydate;
	
	private String printedon;
	
	private Double printrev;
	
	private String proprietor;
	
	private String receiptdate;
	
	private String receiptnum;
	
	private String site;
	
	private String staffyn;
	
	private Double taxpaidytd;
	
	private Double tds;
	
	private String today;

	private String transer;
	
	private String transfer;
	
	private String userid;
	
	private String waracc;
	
	private String warbank;
	
	private String vouNum;
	
	private Integer ddCharges;
	
	private String expiryStatus;
	
	private String oldDepositor;
	
	private List<InchqRequestBean> inchqRequestBean;
}
