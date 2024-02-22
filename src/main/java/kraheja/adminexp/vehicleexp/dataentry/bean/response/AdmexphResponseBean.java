package kraheja.adminexp.vehicleexp.dataentry.bean.response;

import java.time.LocalDateTime;
import java.util.List;

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

public class AdmexphResponseBean {

	private Double average ;
	private String billno ;
	private Double certamount ;
	private String certdate ;
	private String certnum ;
	private Double certrevnum ;
	private String certstatus ;
	private String certtype ;
	private String coy ;
	private Double emeterred ;
	private String equipid ;
	private Double estimatedkm ;
	private Double gasqty ;
	private String gstyn ;
	private String meterid ;
	private String partybilldate ;
	private String partybillref ;
	private String partycode ;
	private String partytype ;
	private String passedon ;
	private Double payamount ;
	private String paydate ;
	private String payref ;
	private Double printed ;
	private String printedon ;
	private String prop ;
	private Double prv_amt ;
	private String prv_certnum ;
	private String prv_date ;
	private String prv_type ;
	private String remarks ;
	private String site ;
	private Double smeterred ;
	private String socid ;
	private LocalDateTime today ;
	private String transer ;
	private Double t_payment ;
	private String userid ;

	private List<AdmexpdResponseBean> admexpdResponseBean;
}