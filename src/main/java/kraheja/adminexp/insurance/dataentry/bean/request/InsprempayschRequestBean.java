package kraheja.adminexp.insurance.dataentry.bean.request;

import javax.validation.Valid;
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

public class InsprempayschRequestBean {

	private String certnum ;
	private String ipaddress ;
	private Double linenumber ;
	private String machinename ;
	private LocalDateTime modifiedon ;
	private String module ;
	private Double payamt ;
	private String paydate ;
	private String policyid ;
	private String policynumber ;
	private String remark ;
	private String site ;
	private String status ;
	private String transer ;
	private String userid ;
	private Boolean isAdd;

}  