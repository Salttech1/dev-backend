package kraheja.fd.deposit.service;

import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.fd.deposit.bean.request.Form15hgRequestBean;

public interface Form15hgService {

	ResponseEntity<ServiceResponseBean> fetchForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(String depositorId, String companyCode, String accountYear, String quater);

	ResponseEntity<ServiceResponseBean> updateForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(Form15hgRequestBean form15hgRequestBean);

	ResponseEntity<ServiceResponseBean> deleteForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(String depositorId, String companyCode, String accountYear, String quater);

	ResponseEntity<ServiceResponseBean> addForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(Form15hgRequestBean form15hgRequestBean);

	ResponseEntity<ServiceResponseBean> fetchUniqueidAndIncomeAndFromDateAndToDate(String depositorId, String companyCode, String accountYear, String quater);

	ResponseEntity<?> export15hgReport(String companyCode, String accountYear, String quater, String fifteenhg, Boolean isIncomeDetails);
}
