package kraheja.purch.bean.request;

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

public class MaterialDetailRequestBean {
	List<AdvrecvoucherRequestBean> advrecvoucherRequestBeanList;
	
	List<AuthmatgroupnarrdtlRequestBean> authmatgroupnarrdtlRequestBeanList;
	
	Auth_HRequestBean authHRequestBean;
	
	List<Auth_DRequestBean> authDRequestBeanList;
}