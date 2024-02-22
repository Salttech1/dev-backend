package kraheja.adminexp.overheads.dataentry.bean.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class OverheaddepositdtlsRequestBean {

	private String adddeduction ;
	private String billtype ;
	private String bldgcode ;
	private String conno ;
	private String connocode ;
	private String coycode ;
	private Double depositeamt ;
	private String period ;
	private String remarks ;
	private String site ;
	private LocalDateTime today ;
	private String transer ;
	private String userid ;
	
	private OverheadconsRequestBean overheadconsRequestBean;
}