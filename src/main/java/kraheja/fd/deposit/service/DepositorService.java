package kraheja.fd.deposit.service;

import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.fd.deposit.bean.request.DepositorRequestBean;

public interface DepositorService {

	ResponseEntity<?> fetchDepositorByCompanycode(String string);

	ResponseEntity<?> fetchDepositorByDepositorIdAndCompanycode(DepositorRequestBean depositorRequestBean);

	ResponseEntity<ServiceResponseBean> addDepositor(DepositorRequestBean depositorRequestBean);

	ResponseEntity<?> updateDepositor(DepositorRequestBean depositorRequestBean);

	ResponseEntity<?> checkDepositorIdAndCompanyCodeExists(String depositorId, String companyCode);

	ResponseEntity<?> ageByDepositorIdAndCompanycode(String depositorId, String companyCode);

	ResponseEntity<?> form15hgByDepositorIdAndCompanycode(String depositorId, String companyCode);
	
	String fetchDepositorAddress(String companyCode, String depositorId);
}
