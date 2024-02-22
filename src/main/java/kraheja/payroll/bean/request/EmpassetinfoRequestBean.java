package kraheja.payroll.bean.request;

import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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

public class EmpassetinfoRequestBean {

	private String assetcode ;
	private String assetdesc ;
	private String assetname ;
	private String authby ;
	private String empcode ;
	private String ipaddress ;
	private String issuedate ;
	private String issuedby ;
	private String machinename ;
	private LocalDateTime modifiedon ;
	private String module ;
	private String receivedby ;
	private String remark ;
	private String returndate ;
	private String site ;
	private String userid ;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}