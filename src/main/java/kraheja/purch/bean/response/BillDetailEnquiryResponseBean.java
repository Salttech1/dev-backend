package kraheja.purch.bean.response;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailEnquiryResponseBean {
	
	String suppbillno;
	
	String rate;

	String authnum;

	Timestamp authdate;

	String amount;

	BigDecimal retention;

	Character billtype;

	BigDecimal advanceadj;

	String ceser;

	String bldgName;

	String partyName;

	String coyName;

	String grpName;

	String quantity	;

	String ser;

	String uom	;

	Timestamp suppbilldt;

	String dequantity;

	String dbqty;
	
	//vat
	List<VatDetailResponseBean> vatDetailResponseBean;
	//chEQUE 
	String  paidAmt;
	
	String  datePaid;

	String  chequeno;
	
	String  bankcode;

	String  paidref;

// tds 
	String  tdsamount;

	String  percentage;

	String  tdschalno;

	String  tdschaldt;
	
	String  tdsbankcode;
	
	String tdscertno;	
	
	String tdcertdt;

	//hundi
	String hundnum;
	
	String hundpartycode;
	
	String hundpartytype;
	
	String hunddocnum;

	String hunddoctser;
	
	String hundpayamt;
	
	String hundbank;
	
	String coyNamehundi;
	
	//bill DebitNote Detail 
	List<BillDebitNoteDetailResponseBean> billDebitNoteDetailResponseBean;
}