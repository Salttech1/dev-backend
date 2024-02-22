package kraheja.sales.bean.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class OutrateResponseBean {

	private Double admincharges ;
	private Double auxiadmin ;
	private Double auxirate ;
	private Double auxi_tds ;
	private String billtype ;
	private String bldgcode ;
	private Double elect ;
	private String enddate ;
	private String flatnum ;
	private Double infra ;
	private Double infradmin ;
	private Double infra_tds ;
	private Double infrrate ;
	private Double maint ;
	private Double maint_tds ;
	private Double natax ;
	private Double oldadmin_notused ;
	private String proponbucaarea ;
	private Double proprate ;
	private Double propratesqft ;
	private Double rate ;
	private String site ;
	private String startdate ;
	private LocalDateTime today ;
	private String userid ;
	private Double water ;
	private String wing ;
	@Default
	private Boolean isDelete = Boolean.FALSE;
}