package kraheja.commons.bean.request;


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

public class OutchqRequestBean {

	private Double amount ;
	private String bank ;
	private Double bunum ;
	private String canceldate ;
	private String coy ;
	private String num ;
	private String origsite ;
	private String origsys ;
	private String partycode ;
	private String partytype ;
	private String paydate ;
	private String paymode ;
	private String payref ;
	private Double printed ;
	private String printedon ;
	private String proprietor ;
	private String recondate ;
	private String reconstmt ;
	private String series ;
	private String site ;
	private String today ;
	private String transer ;
	private String userid ;
}