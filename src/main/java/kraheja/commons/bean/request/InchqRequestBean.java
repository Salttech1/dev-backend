package kraheja.commons.bean.request;

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
public class InchqRequestBean {

	private String num;
	private String coy;
	private String transer;
	private String recnum;
	private String bank;
	private String actype;
	private Double amount;
	private String bouncedate;
	private String bounrevon;
	private String bounrevyn;
	private String canceledate;
	private String coyBank;
	private String date;
	private String chqDate;
	private String fund;
	private String loanyn;
	private String origsite;
	private String origsys;
	private String outstat;
	private String partycode;
	private String paymode;
	private String proprietor;
	private String recondate;
	private String reconstmt;
	private String remark;
	private Double resubcount;
	private String resubdate;
	private String site;
	private String slipnum;
	private LocalDateTime today;
	private String userid;
}
