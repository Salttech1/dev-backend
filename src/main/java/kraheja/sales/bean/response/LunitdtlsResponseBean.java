// Developed By  - 	vikram.p
// Developed on - 16-12-23
// Mode  - Data Entry
// Purpose - Lunitdtls Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bean.response;

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

public class LunitdtlsResponseBean {

	private String amenity ;
	private String conenddate ;
	private String constartdate ;
	private String contactperson ;
	private String conttenuare ;
	private Double depositeamt ;
	private String flatdesc ;
	private String furniture ;
	private String ipaddress ;
	private String lockfromdate ;
	private String locktodate ;
	private String machinename ;
	private String partycode ;
	private String paycycle ;
	private String paysendingmode ;
	private String paystartdate ;
	private String paystatus ;
	private String person_pay ;
	private String person_rec ;
	private String propcode ;
	private String remarks ;
	private Double servtax ;
	private String sharecertstatus ;
	private String site ;
	private Double tds ;
	private LocalDateTime today ;
	private Double unitarea ;
	private String unitflatstatus ;
	private String unitgroup ;
	private String unitid ;
	private String unitno ;
	private String unitstatus ;
	private String unittype ;
	private String userid ;
}