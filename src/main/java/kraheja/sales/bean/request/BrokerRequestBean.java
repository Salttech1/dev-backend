package kraheja.sales.bean.request;

import java.time.LocalDateTime;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.GSTValdiationBean;
import kraheja.commons.bean.request.AddressRequestBean;
import kraheja.commons.bean.request.PartyRequestBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class BrokerRequestBean {

	private String brokCode;
	private Double broklastyr ;
	private Double brokthisyr ;
	private Double broktodate ;
	private Double busslastyr ;
	private Double bussthisyr ;
	private Double busstodate ;
	private String city ;
	private String contactperson ;
	private String designation ;
	private String name ;
	private String origsite ;
	private String rera ;
	private String site ;
	private Double tdslastyr ;
	private Double tdsthisyr ;
	private Double tdstodate ;
	private String title ;
	private LocalDateTime today ;
	private String type ;
	private String userid ;
	@Valid
	private GSTValdiationBean gstValdiationBean;
	private AddressRequestBean addressRequestBean;
	@Valid
	private PartyRequestBean partyRequestBean;
}