package kraheja.fd.deposit.bean.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.fd.deposit.bean.DepositDischargeBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class DepositDischargeRequestBean {

	private List<DepositDischargeBean> depositDischargeBeanList;

	private List<DepositRequestBean> depositRequestBeanList;

	private String depositor;

	private String coy;

	private String userId;

	@Default
	private Boolean isProvision = Boolean.FALSE;
	
	private Long tds;
	
	@Default
	private Boolean isGlEntry = Boolean.FALSE;
}
