package kraheja.adminexp.vehicleexp.dataentry.bean.response;

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

public class AdmexpdResponseBean {

	private Double billamount ;
	private String billdate ;
	private String billref ;
	private Integer bunum ;
	private String certnum ;
	private Double cgst ;
	private Double cgstperc ;
	private String durationfrom ;
	private String durationupto ;
	private String hsmscode ;
	private Double igst ;
	private Double igstperc ;
	private Double sgst ;
	private Double sgstperc ;
	private String site ;
	private Double tds ;
	private Double tdsperc ;
	private LocalDateTime today ;
	private Double ugst ;
	private Double ugstperc ;
	private String userid ;
	private String workcode ;
}