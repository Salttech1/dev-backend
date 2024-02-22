package kraheja.adminexp.insurance.dataentry.bean.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class InspolicyRequestBean {

	private String acholdername ;
	private String acnumber ;
	private String agent ;
	private String assetlocation ;
	private String bank ;
	private String bldgcode ;
	private String branch ;
	private String covunder ;
	private String coycode ;
	private String deductible ;
	private String fromdate ;
	private String ifsc ;
	private String inscoy ;
	private Double insuredval ;
	private String ipaddress ;
	private String it_section ;
	private String machinename ;
	private String maturitydate ;
	private String modifiedon ;
	private String module ;
	private String nominationdet ;
	private String payby ;
	private String paymode ;
	private String personinsured ;
	private String policyid ;
	private String policynumber ;
	private String policysubtype ;
	private String policytype ;
	private String premiumfreq ;
	private String prevpolicyId;
	private String prevpolicynumber ;
	private String renewedyn ;
	private String site ;
	private String staff1 ;
	private String staff2 ;
	private String status ;
	private String userid ;
	List<InsprempayschRequestBean> insprempayschRequestBeanList;
	List <InsassetiteminsuredRequestBean> insassetiteminsuredRequestBeanList;
}