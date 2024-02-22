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

public class UnPostedCertBean {

	private String certnum;
	private String vehtype ;
	private String coy ;
	private String prop;
	private String partycode;
	private String allottedto;
	private String transer;
	
}