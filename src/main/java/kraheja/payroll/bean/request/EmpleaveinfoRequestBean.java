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

public class EmpleaveinfoRequestBean {

	private String acyear ;
	private Double compoffearned ;
	private Double dayexcessadj ;
	private Double daysavailed ;
	private Double daysbf ;
	private Double daysencashed ;
	private Double daysentitled ;
	private String empcode ;
	private String ipaddress ;
	private String leavecode ;
	private String machinename ;
	private Double maxdayscf ;
	private Double maxdaysenc ;
	private LocalDateTime modifiedon ;
	private String module ;
	private String remark ;
	private String site ;
	private String userid ;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}