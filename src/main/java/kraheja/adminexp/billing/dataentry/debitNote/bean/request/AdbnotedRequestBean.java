package kraheja.adminexp.billing.dataentry.debitNote.bean.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdbnotedRequestBean {

	private Double amount;
	private Double cgstamt;
	private Double cgstperc;
	private String dbnoteser;
	private Double discountamt;
	private Double igstamt;
	private Double igstperc;
	private Integer lineno;
	private String origsite;
	private Double quantity;
	private Double rate;
	private String saccode;
	private String sacdesc;
	private Double sgstamt;
	private Double sgstperc;
	private String site;
	private Double taxableamt;
	private LocalDateTime today;
	private Double ugstamt;
	private Double ugstperc;
	private String userid;
	// add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}