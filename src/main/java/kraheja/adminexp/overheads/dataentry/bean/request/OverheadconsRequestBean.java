package kraheja.adminexp.overheads.dataentry.bean.request;

import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.adminexp.overheads.dataentry.bean.response.OverheadmeterResponseBean;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class OverheadconsRequestBean {
	private String ohdhbillcoy ;
	private String ohdhbilltype ;
	private String ohdhbldgcode ;
	private String ohdhconno ;
	private String ohdhconnocode ;
	private String ohdhconsumerno ;
	private Double ohdhdepositeamt ;
	private String ohdhflatnum ;
	private String ohdhload ;
	private String ohdhlocation ;
	private String ohdhpaycoy ;
	private String ohdhsite ;
	private String ohdhstatus ;
	private String ohdhtmpmeteryn ;
	private LocalDateTime ohdhtoday ;
	private String ohdhuserid ;
	private String ohdhvacantflatyn ;
	
	private List<OverheadmeterRequestBean> overheadmeterRequestBeanList;
	private LocationRequestBean locationRequestBean;
	
}
