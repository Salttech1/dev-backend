package kraheja.adminexp.billing.dataentry.adminBill.bean.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.response.AddressResponseBean;
import kraheja.commons.bean.response.PartyResponseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class AdmbillhResponseBean {

	private String acmajor ;
	private String acminor ;
	private String actranser ;
	private Double advnadjust ;
	private Double billamount ;
	private String billtype ;
	private String bldgcode ;
	private String clearacdate ;
	private String clearacperson ;
	private String coy ;
	private Double debitamt ;
	private String exptype ;
	private Double fotoamount ;
	private String fromdate ;
	private String gstrevchgyn ;
	private String mintype ;
	private String narration ;
	private String orderedby ;
	private String origsite ;
	private String origsys ;
	private Double paidamount ;
	private String paiddate ;
	private String paidref ;
	private String partycode ;
	private String partytype ;
	private String passedon ;
	private String project ;
	private String rundate ;
	private String ser ;
	private String site ;
	private String status ;
	private String sunclass ;
	private String sunid ;
	private String suppbilldt ;
	private String suppbillno ;
	private String tdsacmajor ;
	private Double tdsamount ;
	private Double tdsperc ;
	private String todate ;
	private LocalDateTime today ;
	private String userid ;
	
	private PartyResponseBean partyResponseBean;
	private List<AdmbilldResponseBean> admbilldResponseBeanList;
	private List<AdmbillhResponseBean> admbillhResponseBeanList;
	private List<AdmadvanceResponseBean> admadvanceResponseBeanList;
	private AddressResponseBean addressResponseBean;

}                  