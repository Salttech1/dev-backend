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
public class InchqResponseBean {

	private String num;
	private String coy;
	private String transer;
	private String recnum;
	private String bank;
	private String chqkNum;
	private String actype;
	private Double amount;
	private LocalDateTime bouncedate;
	private LocalDateTime bounrevon;
	private String bounrevyn;
	private LocalDateTime canceledate;
	private String coyBank;
	private LocalDateTime date;
	private String fund;
	private String loanyn;
	private String origsite;
	private String origsys;
	private String outstat;
	private String partycode;
	private String paymode;
	private String proprietor;
	private LocalDateTime recondate;
	private String reconstmt;
	private String remark;
	private Double resubcount;
	private LocalDateTime resubdate;
	private String site;
	private String slipnum;
	private LocalDateTime today;
	private String userid;
}
