package kraheja.adminexp.overheads.dataentry.bean;

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
 public class OverheadconsPrvBillDetailsBean {
	 
	Number ohdd_advance;
	Number ohdd_prvadvamt;
	Number Ohdd_PrvActPay;
	Number ohdd_payamt;
	Number ohdd_cumamt;
	Number ohdd_depositeamt;
	Number ohdd_totalbillamtnumber	;
	Number ohdd_totalintrest;
	Number ohdd_totaladvance;
	
	 
}
