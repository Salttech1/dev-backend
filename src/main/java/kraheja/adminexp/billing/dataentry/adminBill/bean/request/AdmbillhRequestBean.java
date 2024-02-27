package kraheja.adminexp.billing.dataentry.adminBill.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class AdmbillhRequestBean {

	private String acmajor ;
	private String acminor ;
	private String actranser ;
	private Double advnadjust ;
	private Double billamount ;
	private String billtype ;
	private String bldgcode ;
	private LocalDate clearacdate ;
	private String clearacperson ;
	private String coy ;
	private Double debitamt ;
	private String exptype ;
	private Double fotoamount ;
	private LocalDate fromdate ;
	private String gstrevchgyn ;
	private String mintype ;
	private String narration ;
	private String orderedby ;
	private String origsite ;
	private String origsys ;
	private Double paidamount ;
	private LocalDate paiddate ;
	private String paidref ;
	private String partycode ;
	private String partytype ;
	private LocalDateTime passedon ;
	private String project ;
	private LocalDate rundate ;
	private String ser ;
	private String site ;
	private String status ;
	private String sunclass ;
	private String sunid ;
	private LocalDate suppbilldt ;
	private String suppbillno ;
	private String tdsacmajor ;
	private Double tdsamount ;
	private Double tdsperc ;
	private LocalDate todate ;
	private LocalDateTime today ;
	private String userid ;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}