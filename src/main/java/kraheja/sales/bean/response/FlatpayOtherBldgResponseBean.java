// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Flatpay Entry / Edit
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

public class FlatpayOtherBldgResponseBean {

	private String otherbldgcode ;
	private Double otherbldgdueamount ;
	private String otherbldgDueDate;
	private String otherbldgflatnum ;
	private String otherbldgnarrative ;
	private String origsite ;
	private String otherbldgownerid ;
	private Double otherbldgpaidamount ;
	private String otherbldgpaiddate ;
	private String otherbldgpaidref ;
	private String site ;
	private LocalDateTime today ;
	private String userid ;
	private String otherbldgwing ;
}