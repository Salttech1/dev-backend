// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Bldgwingmap Entry / Edit
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


public class BldgwingmapResponseBean {

	private String altbldgcode ;
	private String altwing ;
	private String bldgcode ;
	private String bldgwing ;
	private String infrabldgcode ;
	private String infrawing ;
	private String maintbldgcode ;
	private String maintwing ;
	private String origsite ;
	private String site ;
	private LocalDateTime today ;
	private String userid ;
}