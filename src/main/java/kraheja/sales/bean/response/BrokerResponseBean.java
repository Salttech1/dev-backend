package kraheja.sales.bean.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.response.AddressResponseBean;
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

public class BrokerResponseBean {

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

	PartyResponseBean partyResponseBean;

	AddressResponseBean addressResponseBean;
}