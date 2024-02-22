package kraheja.fd.deposit.bean.request;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.fd.deposit.bean.DepintBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class DepintRequestBean {

	private List<DepintBean> depintBeanList;
	private String intFrom;
	private String intUpTo;
	private String coy;
	private String depositorId;
	private String receiptNum;
	private String userid;
}