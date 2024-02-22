package kraheja.adminexp.billing.dataentry.adminBill.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
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

public class AdmadvanceResponseBean {

	private String actranser ;
	private Double advanceamt ;
	private Double basicamt ;
	private String bldgcode ;
	private String coy ;
	private String date ;
	private Double fotoamount ;
	private Double gstamt ;
	private Double gstperc ;
	private String narration ;
	private String orderby ;
	private String origsite ;
	private Double paidamount ;
	private String paiddate ;
	private String paidref ;
	private String partycode ;
	private String partytype ;
	private String passedon ;
	private String pinvdate ;
	private String pinvno ;
	private String project ;
	private String ser ;
	private String site ;
	private String status ;
	private String tdsacmajor ;
	private Double tdsamount ;
	private Double tdsperc ;
	private LocalDateTime today ;
	private String userid ;
	private PartyResponseBean partyResponseBean;
}