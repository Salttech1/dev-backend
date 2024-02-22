package kraheja.payroll.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.response.AddressResponseBean;
import kraheja.payroll.bean.response.EmpassetinfoResponseBean;
import kraheja.payroll.bean.response.EmpeducationResponseBean;
import kraheja.payroll.bean.response.EmpexperienceResponseBean;
import kraheja.payroll.bean.response.EmpfamilyResponseBean;
import kraheja.payroll.bean.response.EmpjobinfoResponseBean;
import kraheja.payroll.bean.response.EmpleaveinfoResponseBean;
import kraheja.payroll.bean.response.EmppersonalResponseBean;
import kraheja.payroll.bean.response.EmpreferenceResponseBean;
import kraheja.payroll.bean.response.EmpsalarypackageResponseBean;
import kraheja.payroll.bean.response.EmpschemeinfoResponseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeDetailsResponseBean {
	private List<EmppersonalResponseBean> emppersonalResponseBean;
	private List<EmpeducationResponseBean> empeducationResponseBean;
	private List<EmpjobinfoResponseBean> empjobinfoResponseBean;
	private List<EmpsalarypackageEarnDedBean> empsalarypackageResponseBean;
	private List<EmpsalarypackageEarnDedBean> empsalarypackagededResponseBean;
	private List<EmpschemeinfoResponseBean> empschemeinfoResponseBean;
	private List<EmpleaveinfoResponseBean> empleaveinfoResponseBean;
	private List<EmpfamilyResponseBean> empfamilyResponseBean;
	private List<EmpexperienceResponseBean> empexperienceResponseBean;
	private List<EmpreferenceResponseBean> empreferenceResponseBean;
	private List<EmpassetinfoResponseBean> empassetinfoResponseBean;
	private AddressResponseBean addressmail;
	private AddressResponseBean addressres;
	private byte[] EmpPhoto;
	List MTHLYGROSS;
	List CTC;
	String FirstSalaryYN;
}