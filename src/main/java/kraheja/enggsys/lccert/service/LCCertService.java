package kraheja.enggsys.lccert.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import kraheja.enggsys.bean.request.LCCertPrintRequestBean;
import kraheja.enggsys.bean.request.LCCertViewRequestBean;
import kraheja.enggsys.bean.request.LCCertPrintStatusUpdateDetailRequestBean;
import kraheja.enggsys.bean.response.LCCertPrintDetailResponseBean;
import kraheja.purch.bean.request.MaterialPaymentPrintRequestBean;

public interface LCCertService {
	
	ResponseEntity<?> mergePdf(LCCertPrintDetailResponseBean lcCertPrintDetailResponseBean);

	ResponseEntity<?> insertIntoMaterialPaymentTemp(LCCertPrintRequestBean lcCertPrintRequestBean);

	ResponseEntity<?> updateLCcertPrintStatus(LCCertPrintStatusUpdateDetailRequestBean LCCertprintStatusUpdateDetailRequestBean);

	ResponseEntity<?> deleteTempTableFromSessionId(Integer sessionId);
	
}
