package kraheja.payroll.bean.response;
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
public class ReportParametersRequestBean {
	private String  fromcoy;
	private String  tocoy;
	private String  fromdept;
	private String  todept;
	private String  fromEmptype;
	private String  toEmptype;
	private String  fromPayType;
	private String  toPayType;
	private String  fromEmpcode;
	private String  toEmpcode;
	private String  frompaymonth;
	private String  topaymonth;
	private String  fromLocation;
	private String  toLocation;
	private String  fromWorksite;
	private String  toWorksite;
	private String  hotelYN;
}