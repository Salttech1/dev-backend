package kraheja.enggsys.bean.response;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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

public class CertResponseBean {

	private Double advadjusted ;
	private String asstaxfrom ;
	private String asstaxupto ;
	private Integer basicamt ;
	private Double billamount ;
	private String billdate ;
	private String billnos ;
	private String billref ;
	private String bldgcode ;
	private Double certamount ;
	private String certdate ;
	private String certnum ;
	private Integer certrevnum ;
	private String certstatus ;
	private String certtype ;
	private String cfcode ;
	private String cfgroup ;
	private String city ;
	private String clearacyn ;
	private Double conamount ;
	private String contract ;
	private String coy ;
	private Double debit ;
	private String debitingparty ;
	private String debitingreason ;
	private String debsocyn ;
	private String description ;
	private String domain ;
	private String durfrom ;
	private String durto ;
	private String equipid ;
	private String flat ;
	private Integer krishicessamt ;
	private Integer krishicessperc ;
	private String mainmatgroup ;
	private String mainparty ;
	private String mainrecid ;
	private String matcode ;
	private String matgroup ;
	private String misbldg ;
	private String misproject ;
	private Double mwctaxamount ;
	private Double orconamount ;
	private String orcontract ;
	private String originator ;
	private String origsite ;
	private String orpartycode ;
	private String orpartytype ;
	private String partycode ;
	private String partytype ;
	private String passedon ;
	private Double payamount ;
	private String paydate ;
	private String payref ;
	private String paytender ;
	private Double perdone ;
	private Integer printed ;
	private String printedon ;
	private String project ;
	private String prop ;
	private String property ;
	private String provyn ;
	private Double prvamt ;
	private String prvcertnum ;
	private String prvdate ;
	private String prvtype ;
	private String remarks ;
	private String reqdate ;
	private String reqnum ;
	private Double retained ;
	private Integer runser ;
	private Integer servicetaxamt ;
	private Integer servicetaxperc ;
	private String site ;
	private String socid ;
	private Integer swachhcessamt ;
	private Integer swachhcessperc ;
	private String taxlevel ;
	private Double tdsamount ;
	private Double tdssur ;
	private LocalDateTime today ;
	private Double tottwoptc ;
	private Double tottwtptc ;
	private String transer ;
	private Integer trips ;
	private Double tadvadj ;
	private Double tadvos ;
	private Double tadvpaid ;
	private Double tdebit ;
	private Double tdebsoc ;
	private Double tmwctaxamt ;
	private Double tpayment ;
	private Double tperpaid ;
	private Double tretained ;
	private Double ttdsamt ;
	private Double ttdssur ;
	private Double ttrips ;
	private Double twriteoffamt ;
	private String userid ;
	private Integer vatamt ;
	private Integer vatperc ;
	private String wing ;
	private String workcode ;
	private String workgroup ;
	private Double writeoffamt ;
	private PartyResponseBean partyResponseBean;
}