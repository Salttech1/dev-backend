package kraheja.adminexp.vehicleexp.dataentry.bean.response;

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

public class UnPosterCertDetailBean {
	
	private String certnum;
	private String workcode;
	private String billref;
	private String billdate;
	private String billamount;
	private String durationFrom;
	private String durationUpto;

}
