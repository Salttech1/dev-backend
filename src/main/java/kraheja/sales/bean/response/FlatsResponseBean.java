package kraheja.sales.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class FlatsResponseBean {

	private String accomtype ;
	private Double agprice ;
	private Double amtos ;
	private Double amtrec ;
	private Double bamenarea ;
	private String bldgcode ;
	private Double bparkarea ;
	private String broker ;
	private Double bteraarea ;
	private Double bunitarea ;
	private Double camenarea ;
	private String config ;
	private LocalDateTime contracton ;
	private String coy ;
	private Double cparkarea ;
	private Double cteraarea ;
	private Double cunitarea ;
	private Double curera ;
	private String custid ;
	private String custtype ;
	private Double discount ;
	private Double enclbalcrera ;
	private String flatnum ;
	private String flatpark ;
	private String floor ;
	private LocalDateTime ho2owner ;
	private Double intrate ;
	private String leasedto ;
	private String leaseref ;
	private Double loanamt ;
	private String loanbranch ;
	private LocalDateTime loanclosedate ;
	private String loanco ;
	private String loannum ;
	private Double loanpaid ;
	private String loanyn ;
	private Double maintrate ;
	private String mflatbldg ;
	private String mflatno ;
	private String mflatwing ;
	private LocalDateTime mpaiddate ;
	private String mpaidref ;
	private String mpaidyymm ;
	private LocalDateTime nocdt ;
	private LocalDateTime nocrcvddate ;
	private String noctype ;
	private LocalDateTime occupdate ;
	private String origcoy ;
	private String origsite ;
	private LocalDateTime overon ;
	private String ownerid ;
	private String poacode ;
	private String poaname ;
	private Double proptax ;
	private String psind ;
	private Double ratesft ;
	private Double rebaterfnd ;
	private LocalDateTime refunddate ;
	private String remarks ;
	private String salestatus ;
	private String saletype ;
	private String site ;
	private String soldyn ;
	private Double stampduty ;
	private LocalDateTime today ;
	private String ufdiscount ;
	private String userid ;
	private String wing ;
	private LocalDateTime xtradate ;
	private Double xtrarfnd ;
}