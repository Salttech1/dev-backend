package kraheja.purch.bean.response;

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

public class MatcertestimateactualResponseBean {

	private String actionby ;
	private Double actworkdone ;
	private Double contractval ;
	private Double contvaladj ;
	private String deliverydate ;
	private String deliveryweeks ;
	private Double estimateamt ;
	private String groupcode ;
	private String logicnotenum ;
	private String matcertcode ;
	private String matcerttype ;
	private String orderdate ;
	private Double othdebits ;
	private String partycode ;
	private String partytype ;
	private String projcode ;
	private String quotedate ;
	private String site ;
	private String subgroupcode ;
	private String tenderdate ;
	private LocalDateTime today ;
	private String userid ;
}