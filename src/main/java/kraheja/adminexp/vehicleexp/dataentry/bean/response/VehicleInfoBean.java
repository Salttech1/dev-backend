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

public class VehicleInfoBean {

	private String vehtype ;
	private String eqpAllottedto ;
	private String eqpCoy ;
	private String coyname ;
	private String gstno ;
	private String eqpProp ;
	private String propname ;
	private Double startmeter;
	
}