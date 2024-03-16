package kraheja.payroll.bean;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.request.AddressRequestBean;
import kraheja.commons.bean.request.PartyRequestBean;
import kraheja.payroll.bean.request.EmpassetinfoRequestBean;
import kraheja.payroll.bean.request.EmpeducationRequestBean;
import kraheja.payroll.bean.request.EmpexperienceRequestBean;
import kraheja.payroll.bean.request.EmpfamilyRequestBean;
import kraheja.payroll.bean.request.EmpjobinfoRequestBean;
import kraheja.payroll.bean.request.EmpleaveinfoRequestBean;
import kraheja.payroll.bean.request.EmppersonalRequestBean;
import kraheja.payroll.bean.request.EmpreferenceRequestBean;
import kraheja.payroll.bean.request.EmpsalarypackageRequestBean;
import kraheja.payroll.bean.request.EmpschemeinfoRequestBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeDetailsRequestBean {
	private EmppersonalRequestBean emppersonalRequestBean;
	private List<EmpeducationRequestBean> empeducationRequestBean;
	private EmpjobinfoRequestBean empjobinfoRequestBean;
	private List<EmpsalarypackageRequestBean> empsalarypackageRequestBean;
	private List<EmpsalarypackageRequestBean> empsalarypackagededRequestBean;
	private List<EmpschemeinfoRequestBean> empschemeinfoRequestBean;
	private List<EmpleaveinfoRequestBean> empleaveinfoRequestBean;
	private List<EmpfamilyRequestBean> empfamilyRequestBean;
	private List<EmpexperienceRequestBean> empexperienceRequestBean;
	private List<EmpreferenceRequestBean> empreferenceRequestBean;
	private List<EmpassetinfoRequestBean> empassetinfoRequestBean;
	private AddressRequestBean addressmail;
	private AddressRequestBean addressres;
	private PartyRequestBean partyRequestBean;
	String modifiedDate;
}