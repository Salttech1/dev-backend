package kraheja.adminexp.insurance.dataentry.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class InsassetiteminsuredResponseBean {

	private String bldgcode ;
	private String brand ;
	private Double epcgvalue ;
	private String groupcode ;
	private String groupname ;
	private String ipaddress ;
	private String item ;
	private String itemdesc ;
	private String itemserial ;
	private Double linenumber ;
	private String machinename ;
	private LocalDateTime modifiedon ;
	private String module ;
	private String policyid ;
	private String policynumber ;
	private String purchasedate ;
	private String purchaseyear ;
	private Double qty ;
	private String qtydesc ;
	private String remark ;
	private Double revisedvalue ;
	private String site ;
	private String srno ;
	private String subgroupcode ;
	private String subgroupname ;
	private String userid ;
	private Double value ;
	private Double valuewithduty ;
}    