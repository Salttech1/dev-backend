package kraheja.fd.deposit.bean.request;

import javax.validation.Valid;

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
public class DepositTransferRequestBean {
	
	private DepositRequestBean depositRequestBean;
	
	@Valid
	private DepositorRequestBean depositorRequestBean;
}
