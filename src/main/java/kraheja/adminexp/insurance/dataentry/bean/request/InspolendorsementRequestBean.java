package kraheja.adminexp.insurance.dataentry.bean.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class InspolendorsementRequestBean {

	private String certnum ;
	private String certstat ;
	private String endorsetp ;
	private String endrdetail ;
	private String endrfrdt ;
	private Double endrinsval ;
	private String endrpayby ;
	private String endrpaymode ;
	private Double endrpremium ;
	private String endruptodt ;
	private String ipaddress ;
	private String machinename ;
	private String modifiedon ;
	private String module ;
	private String polendorsedt ;
	private String polendorseid ;
	private String polendorsenum ;
	private String policyid ;
	private String policyno ;
	private String site ;
	private String transer ;
	private String userid ;
	
	//List<InspolicyRequestBean> inspolicyRequestBeanList;
}