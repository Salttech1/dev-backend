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

public class EmpjobinfoRequestBean {

	private String appliedon ;
	private String appointedby ;
	private String appointedon ;
	private String bank ;
	private String bankacno ;
	private String bankactype ;
	private String bankbranch ;
	private String branch ;
	private String company ;
	private String costcentre ;
	private String department ;
	private String deputedincoy ;
	private String desigpost ;
	private String desigtax ;
	private String directoryn ;
	private String division ;
	private Integer dutyhours ;
	private String effectivefrom ;
	private String effectiveupto ;
	private String empcode ;
	private String emptype ;
	private String grade ;
	private String gratuitydate ;
	private String gratuitypaymonth ;
	private String ipaddress ;
	private String jobfrom ;
	private String jobstatus ;
	private String jobtype ;
	private String jobupto ;
	private String joindate ;
	private String joinedyn ;
	private String level ;
	private String location ;
	private String machinename ;
	private LocalDateTime modifiedon ;
	private String module ;
	private Integer noticedays ;
	private String oldbank ;
	private String oldbankacno ;
	private String origjoindate ;
	private String paymode ;
	private String prevcoy ;
	private String probationupto ;
	private Integer probmonths ;
	private String punchcardno ;
	private String reasonofleav ;
	private String region ;
	private String remark ;
	private String reportingto ;
	private String salarygrpcode ;
	private String section ;
	private String settlementdate ;
	private String shiftcode ;
	private String site ;
	private String termdate ;
	private String ticketno ;
	private String traineeupto ;
	private String userid ;
	private String worksite ;
	private String xferdate ;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}