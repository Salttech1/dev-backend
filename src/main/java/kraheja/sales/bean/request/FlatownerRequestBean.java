// Developed By  - 	sandesh.c
// Developed on - 17-08-23
// Mode  - Data Entry
// Purpose - Flatowner Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bean.request;

import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class FlatownerRequestBean {

	private String aadharno ;
	private Double adminrate ;
	private Double auxiadmin ;
	private Integer auximonths ;
	private Double auxirate ;
	private String billmode ;
	private String bldgcode ;
	private String cencard ;
	private String city ;
	private String custtype ;
	private Double elect ;
	private String flatnum ;
	private String floor ;
	private String gstno ;
	private Double infradmin ;
	private Integer inframonths ;
	private Double infrrate ;
	private Double maintrate ;
	private String name ;
	private Double natax ;
	private String nreacnum ;
	private String nrebank ;
	private String nriipi7 ;
	private String nrinat ;
	private String nripass ;
	private String nripassiss ;
	private String nripedate ;
	private String nripnat ;
	private String nrippidate ;
	private String nriprof ;
	private String nriteloff ;
	private String nritelres ;
	private String nroacnum ;
	private String nrobank ;
	private String ogendmm ;
	private Double ogintpaid ;
	private Integer ogmonths ;
	private String ogstartmm ;
	private String origsite ;
	private String ownerid ;
	private String ownertype ;
	private String panno ;
	private String poanat ;
	private String poapass ;
	private String poapassiss ;
	private String poappidate ;
	private String poaprof ;
	private String relation ;
	private String site ;
	private String title ;
	private LocalDateTime today ;
	private String township ;
	private String userid ;
	private String vipyn ;
	private Double water ;
	private String wing ;
	private String insertUpdateMode;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}