package kraheja.enggsys.bean.request;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.purch.bean.request.AdvrecvoucherRequestBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor	

public class CertificateRequestBean {	

	List<AdvrecvoucherRequestBean> advrecvoucherRequestBeanList;

	List<CertworknarrdtlRequestBean> certworknarrdtlRequestBeanList;

    CertRequestBean certRequestBean;
}