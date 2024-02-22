package kraheja.purch.bean.response;

import java.sql.Timestamp;

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

public class BillDebitNoteDetailResponseBean {
	String  suppbillno;

	String  partycode;
	
	String  dbnoteser;
	
	String amount;

	String  tdsamount;

	Timestamp  date;

	String  quantity;
}