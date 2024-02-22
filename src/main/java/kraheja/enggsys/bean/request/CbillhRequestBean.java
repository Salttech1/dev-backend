package kraheja.enggsys.bean.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class CbillhRequestBean {

	private Double advanceadj ;
	private Double advancepaid ;
	private Double amount ;
	private String bankcode ;
	private String billtype ;
	private String bldgcode ;
	private String certdate ;
	private String certnum ;
	private String chequeno ;
	private String contbilldt ;
	private String contbillno ;
	private String contract ;
	private String coy ;
	private Integer crdays ;
	private String date ;
	private Double debitamt ;
	private String debsocyn ;
	private String durfrom ;
	private String durto ;
	private Double freight ;
	private String misbldg ;
	private String misproject ;
	private String narration ;
	private String omsserviceyn ;
	private String origsite ;
	private Double othercharges ;
	private Double packing ;
	private Double paidamt ;
	private String paiddate ;
	private String paidref ;
	private String partycode ;
	private String partytype ;
	private String project ;
	private String prop ;
	private String property ;
	private Double retainos ;
	private Double retention ;
	private String ser ;
	private String site ;
	private String tdcertdt ;
	private Double tdsamount ;
	private String tdsbankcode ;
	private String tdscertno ;
	private String tdschaldt ;
	private String tdschalno ;
	private Double tdsperc ;
	private Integer tdsprint ;
	private LocalDateTime today ;
	private Double transport ;
	private String userid ;
	private String workcode ;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}