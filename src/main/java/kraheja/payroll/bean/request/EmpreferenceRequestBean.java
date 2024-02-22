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

public class EmpreferenceRequestBean {

	private String companyname ;
	private String empcode ;
	private String ipaddress ;
	private String knownfrom ;
	private String machinename ;
	private LocalDateTime modifiedon ;
	private String module ;
	private String post ;
	private String referenceaddress ;
	private String referencecellno ;
	private String referencetelno ;
	private String refrelation ;
	private String refrencename ;
	private String site ;
	private Integer srno ;
	private String userid ;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}