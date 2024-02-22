package kraheja.enggsys.bean.request;

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

public class LCAuthRequestBean {

	private String bldgcode;
	private String partytype;
	private String partyname;
	private String coy;
	private String authtype;
	private String authdate;
	private String partycode ;
	private String authnum ;
	private String passedon ;
	private String status ;
	private String prop ;
	private String project ;
	private String property ;
	private Double amount ;
	private String matgroup ;
	private String transer ;
	private Double tdsamount ;
	private Double advadjust ;
	private Double retained ;
	private Double relretain ;
	private Double payamount ;
	private String matname;
	private String bank;
	private String chqDate;
	private String chqnum;
	private String outstat;
}