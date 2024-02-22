package kraheja.commons.bean;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ActranhBean {
	
	private String transer;
	
	private String coy;
	
	private String proprietor;
	
	private String trantype;
	
	private String vounum;
	
	private String trandate;
	
	private String coytranser;
	
	private String balancedyn;

	private String bankcode;

	private String bbookyn;

	private String cbookyn;

	private String clearacyn;

	private String closingjvyn;

	private Double crepno;

	private String ledgcode;

	private String narrative;

	private String partycode;

	private String partytype;

	private String postedyn;

	private String provyn;

	private String reverseyn;

	private String site;

	private LocalDateTime today;

	private Double tranamt;

	private String userid;

	private String voudate;
}
