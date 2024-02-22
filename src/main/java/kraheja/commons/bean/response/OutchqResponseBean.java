package kraheja.commons.bean.response;

import java.time.LocalDateTime;

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

public class OutchqResponseBean {

	private Double amount ;
	private String bank ;
	private Double bunum ;
	private LocalDateTime canceldate ;
	private String coy ;
	private String num ;
	private String origsite ;
	private String origsys ;
	private String partycode ;
	private String partytype ;
	private LocalDateTime paydate ;
	private String paymode ;
	private String payref ;
	private Double printed ;
	private LocalDateTime printedon ;
	private String proprietor ;
	private LocalDateTime recondate ;
	private String reconstmt ;
	private String series ;
	private String site ;
	private LocalDateTime today ;
	private String transer ;
	private String userid ;
}