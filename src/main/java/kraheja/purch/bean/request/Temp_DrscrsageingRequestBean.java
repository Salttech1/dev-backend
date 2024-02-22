package kraheja.purch.bean.request;

import java.time.LocalDate;

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

public class Temp_DrscrsageingRequestBean {

	private Double days0to365 ;
	private Double days366to1095 ;
	private Double above1095 ;
	private Double above180 ;
	private Double advance ;
	private LocalDate billdate ;
	private String billno ;
	private String ipaddress ;
	private String machinename ;
	private String modifiedon ;
	private String module ;
	private String partycode ;
	private Double retention ;
	private Double sesid ;
	private String site ;
	private Double srno ;
	private String trantype ;
	private String userid ;
}