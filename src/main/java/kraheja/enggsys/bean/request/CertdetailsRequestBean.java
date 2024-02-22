package kraheja.enggsys.bean.request;
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

public class CertdetailsRequestBean {

	private Double advadjusted ;
	private Double billamount ;
	private String certnum ;
	private String contbillno ;
	private String contract ;
	private String origsite ;
	private Double relretamt ;
	private String site ;
	private Double tdsamt ;
	private LocalDateTime today ;
	private String userid ;
}