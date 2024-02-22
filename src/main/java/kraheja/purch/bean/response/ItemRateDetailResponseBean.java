package kraheja.purch.bean.response;

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

public class ItemRateDetailResponseBean {
	
	String partyName;
	
	String date;
	
	String rate;
	
	String stper;
	
	String exciseper;

	String discper;

}