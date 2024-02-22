package kraheja.purch.bean.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.request.PartyRequestBean;
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

public class PbillhRequestBean {

	private Double advanceadj ;
	private Double advancepaid ;
	private Double amount ;
	private String authdate ;
	private String authnum ;
	private String bankcode ;
	private String billtype ;
	private String bldgcode ;
	private String chequeno ;
	private String coy ;
	private Integer crdays ;
	private String date ;
	private Double debitamt ;
	private String debsocyn ;
	private String misbldg ;
	private String misproject ;
	private String narration ;
	private String omspurcyn ;
	private String orderedby ;
	private String origsite ;
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
	private String state ;
	private String site ;
	private String suppbilldt ;
	private String suppbillno ;
	private Double tcsamount ;
	private String tdcertdt ;
	private Double tdsamount ;
	private String tdsbankcode ;
	private String tdscertno ;
	private String tdschaldt ;
	private String tdschalno ;
	private Double tdsperc ;
	private Integer tdsprint ;
	private LocalDateTime today ;
	private String userid ;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;

	private PbilldRequestBean pbilldRequestBean;

	private List<PbillvatRequestBean> pbillvatRequestBean;

	private List<DcRequestBean> dcRequestBean;
	
	private PartyRequestBean partyRequestBean;
}