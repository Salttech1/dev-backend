package kraheja.adminexp.overheads.dataentry.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class OverheadtxnResponseBean {
	private Double advance ;
	private String bilentdate ;
	private Double billamt ;
	private String billno ;
	private String billperiod ;
	private String billtranser ;
	private Double cgst ;
	private Double cgstper ;
	private String connocode ;
	private Double cumamt ;
	private String custbilldate ;
	private String fromdate ;
	private String todate ;
	private String hsncode ;
	private Double igst ;
	private Double igstper ;
	private Double intrest ;
	private Double noofprint ;
	private Double payamt ;
	private String paydate ;
	private String payref ;
	private String printflag ;
	private Double prvactpay ;
	private Double prvadvamt ;
	private String remarks ;
	private Double sgst ;
	private Double sgstper ;
	private String site ;
	private String supplementarybill ;
	private LocalDateTime today ;
	private String transer ;
	private String unitno ;
	private String userid ;
	
	private List<OverheadmeterResponseBean> overheadmeterResponseBeanList; 
	private OverheadconsResponseBean overheadconsResponseBean; 
}
