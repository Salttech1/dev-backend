package kraheja.purch.bean.response;

import java.time.LocalDateTime;

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

public class PbilldResponseBean {

	private Double amount ;
	private String authdate ;
	private String authnum ;
	private String bldgcode ;
	private String ceser ;
	private Double dbqty ;
	private Double dequantity ;
	private Double derate ;
	private String deuom ;
	private Double grnln ;
	private String grnno ;
	private Double indln ;
	private String indno ;
	private String itemcode ;
	private Integer lineno ;
	private String lotno ;
	private String matcode ;
	private String matgroup ;
	private String origsite ;
	private String partycode ;
	private Double poln ;
	private String pono ;
	private Double quantity ;
	private Double rate ;
	private String ser ;
	private String site ;
	private String suppbilldt ;
	private String suppbillno ;
	private LocalDateTime today ;
	private String uom ;
	private String userid ;
}