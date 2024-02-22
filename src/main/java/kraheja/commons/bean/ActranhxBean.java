package kraheja.commons.bean;

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

public class ActranhxBean {

	private String bankcode ;
	private String coy ;
	private Double crepno ;
	private String ledgcode ;
	private String narrative ;
	private Double nrepno ;
	private String partycode ;
	private String partytype ;
	private String postedyn ;
	private String proprietor ;
	private String revision ;
	private String site ;
	private Double tranamt ;
	private String trandate ;
	private String transer ;
	private String trantype ;
	private String userid ;
	private String voudate ;
	private String vounum ;
}