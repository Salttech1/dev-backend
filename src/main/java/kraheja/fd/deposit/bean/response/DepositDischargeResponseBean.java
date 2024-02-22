package kraheja.fd.deposit.bean.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.fd.deposit.bean.DepositDischargeBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class DepositDischargeResponseBean {

	List<DepositDischargeBean> depositDischargeBeanList;

	Map<String, String> narrationMap;

	Long interestAmountForMarch;

	Long interestAmountForApril;

	Long tdsAmountForMarch;

	Long tdsAmountForApril;

	Long tds;

	String intUptoDate;
}
