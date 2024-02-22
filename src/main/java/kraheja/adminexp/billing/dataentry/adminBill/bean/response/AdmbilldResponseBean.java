package kraheja.adminexp.billing.dataentry.adminBill.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import kraheja.commons.bean.GSTValdiationBean;
import kraheja.commons.bean.response.PartyResponseBean;
import kraheja.commons.bean.response.MinorsResponseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class AdmbilldResponseBean {

	private Double amount ;
	private Double cgstamt ;
	private Double cgstperc ;
	private Double dbamt ;
	private Double dbqty ;
	private Double discountamt ;
	private String hsnsaccode ;
	private Double igstamt ;
	private Double igstperc ;
	private String itemdesc ;
	private Integer lineno ;
	private String origsite ;
	private Double quantity ;
	private Double rate ;
	private String ser ;
	private Double sgstamt ;
	private Double sgstperc ;
	private String site ;
	private Double taxableamt ;
	private LocalDateTime today ;
	private Double ugstamt ;
	private Double ugstperc ;
	private String uom ;
	private String userid ;
	private PartyResponseBean partyResponseBean;
	private MinorsResponseBean minorsResponseBean;
}     