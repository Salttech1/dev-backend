package kraheja.enggsys.bean.response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.enggsys.bean.CertificateDetailBean;
import kraheja.purch.bean.response.AdvrecvoucherResponseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class CertificateEntryResponseBean {
//	CertResponseBean certResponseBean;

	List<CertworknarrdtlResponseBean> certworknarrdtlResponseBeanList;
	
	List<AdvrecvoucherResponseBean> advrecvoucherResponseBeanList;
	
	List<CertdetailsResponseBean> certdetailsResponseBeanList;
	
	CertificateDetailBean certificateDetailBean;
	Double spendSoFar;
	Double priSpentAmount;
	 String priMisProject;
	 String priMisbldg;
	 String writeoffamt;
	Double vatamt;
	Double certamt;


} 