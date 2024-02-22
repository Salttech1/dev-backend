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

public class EmpfamilyRequestBean {

	private String birthdate ;
	private String empcode ;
	private String fullname ;
	private String ipaddress ;
	private String machinename ;
	private LocalDateTime modifiedon ;
	private String module ;
	private String occupation ;
	private String relation ;
	private String site ;
	private Integer srno ;
	private String userid ;
	private String weddingdate ;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}