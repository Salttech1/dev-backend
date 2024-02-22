package kraheja.purch.bean.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MatGroupResponseBean {

	private Boolean level2 ;
	private String uom;
	private Integer qty;
	private Integer rate;
	
	@Default
	private Boolean setBillDetailValues = Boolean.TRUE;
}