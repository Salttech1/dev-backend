package kraheja.enggsys.lcauth.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import kraheja.enggsys.bean.request.LCAuthPrintRequestBean;
import kraheja.enggsys.bean.request.LCAuthViewRequestBean;
import kraheja.enggsys.bean.request.LCAuthPrintStatusUpdateDetailRequestBean;
import kraheja.enggsys.bean.response.LCAuthPrintDetailResponseBean;
import kraheja.purch.bean.request.MaterialPaymentPrintRequestBean;

public interface LCAuthService {
	
	ResponseEntity<?> mergePdf(LCAuthPrintDetailResponseBean lcAuthPrintDetailResponseBean);

	ResponseEntity<?> insertIntoMaterialPaymentTemp(LCAuthPrintRequestBean lcAuthPrintRequestBean);

	ResponseEntity<?> updateLCAuthPrintStatus(LCAuthPrintStatusUpdateDetailRequestBean LCAuthprintStatusUpdateDetailRequestBean);

	ResponseEntity<?> deleteTempTableFromSessionId(Integer sessionId);
	
}
