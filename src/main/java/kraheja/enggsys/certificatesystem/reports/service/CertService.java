package kraheja.enggsys.certificatesystem.reports.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import kraheja.enggsys.bean.request.CertPrintRequestBean;
import kraheja.enggsys.bean.request.CertViewRequestBean;
import kraheja.enggsys.bean.request.CertPrintStatusUpdateDetailRequestBean;
import kraheja.enggsys.bean.response.CertPrintDetailResponseBean;
import kraheja.purch.bean.request.MaterialPaymentPrintRequestBean;

public interface CertService {
	
	ResponseEntity<?> mergePdf(CertPrintDetailResponseBean CertPrintDetailResponseBean);

	ResponseEntity<?> insertIntoMaterialPaymentTemp(CertPrintRequestBean CertPrintRequestBean);

	ResponseEntity<?> updateCertPrintStatus(CertPrintStatusUpdateDetailRequestBean CertprintStatusUpdateDetailRequestBean);

	ResponseEntity<?> deleteTempTableFromSessionId(Integer sessionId);
	
}
