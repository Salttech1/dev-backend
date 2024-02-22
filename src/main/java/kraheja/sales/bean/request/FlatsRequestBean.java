package kraheja.sales.bean.request;

import javax.validation.Valid;
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

public class FlatsRequestBean {

	private String accomtype ;
	private Double agprice ;
	private Double amtos ;
	private Double amtrec ;
	private String bldgcode ; //NS 22.02.2023
	private Double bamenarea ;
	private Double bparkarea ;
	private String broker ;
	private Double bteraarea ;
	private Double bunitarea ;
	private Double camenarea ;
	private String config ;
	private String contracton ;
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
	private String ho2owner ;
	private Double intrate ;
	private String leasedto ;
	private String leaseref ;
	private Double loanamt ;
	private String loanbranch ;
	private String loanclosedate ;
	private String loanco ;
	private String loannum ;
	private Double loanpaid ;
	private String loanyn ;
	private Double maintrate ;
	private String mflatbldg ;
	private String mflatno ;
	private String mflatwing ;
	private String mpaiddate ;
	private String mpaidref ;
	private String mpaidyymm ;
	private String nocdt ;
	private String nocrcvddate ;
	private String noctype ;
	private String occupdate ;
	private String origcoy ;
	private String origsite ;
	private String overon ;
	private String ownerid ;
	private String poacode ;
	private String poaname ;
	private Double proptax ;
	private String psind ;
	private Double ratesft ;
	private Double rebaterfnd ;
	private String refunddate ;
	private String remarks ;
	private String salestatus ;
	private String saletype ;
	private String site ;
	private String soldyn ;
	private Double stampduty ;
	private String today ;
	private String ufdiscount ;
	private String userid ;
	private String wing ;
	private String xtradate ;
	private Double xtrarfnd ;
	//following two fields are not in the database, these are required to get the old data of wing and flatnumber columns within update query.
	private String oldflatnum; //NS 17.02.2023, added this after consulting my logic with YC(neosoft) on call at 11:26
	private String oldwing; //NS 17.02.2023, added this after consulting my logic with YC(neosoft) on call at 11:26
	//following one field is used to determine which operation needs to be performed for flat record
	private String dboperation;
}