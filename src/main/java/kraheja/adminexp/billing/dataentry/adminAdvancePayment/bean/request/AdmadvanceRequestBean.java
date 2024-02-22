package kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.request;

import java.time.LocalDate;
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

public class AdmadvanceRequestBean {

	private String actranser;
	private Double advanceamt;
	private Double basicamt;
	private String bldgcode;
	private String coy;
	private LocalDate date;
	private Double fotoamount;
	private Double gstamt;
	private Double gstperc;
	private String narration;
	private String orderby;
	private String origsite;
	private Double paidamount;
	private LocalDate paiddate;
	private String paidref;
	private String partycode;
	private String partytype;
	private LocalDate passedon;
	private LocalDate pinvdate;
	private String pinvno;
	private String project;
	private String ser;
	private String site;
	private String status;
	private String tdsacmajor;
	private Double tdsamount;
	private Double tdsperc;
	private LocalDateTime today;
	private String userid;
}