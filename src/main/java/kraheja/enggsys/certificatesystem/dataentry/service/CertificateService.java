package kraheja.enggsys.certificatesystem.dataentry.service;


import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.enggsys.bean.request.CdbnotehRequestBean;
import kraheja.enggsys.bean.request.CertificateRequestBean;
import kraheja.enggsys.bean.request.ContractRequestBean;
import kraheja.enggsys.bean.request.ContractdebitRequestBean;

public interface CertificateService {

	ResponseEntity<ServiceResponseBean> fetchContractDetailsByRecId(String recId) ;

	ResponseEntity<ServiceResponseBean> fetchCoynameForEnggsys(String bldgcode);

	ResponseEntity<ServiceResponseBean> addContractDetail(ContractRequestBean contractRequestBean);

	ResponseEntity<ServiceResponseBean> fetchCertnumRecid(String recid);

	ResponseEntity<ServiceResponseBean> fetchCertificateEntryByCertnum(String certnum);

	ResponseEntity<ServiceResponseBean> saveContractDetail(ContractRequestBean contractRequestBean);
	
	ResponseEntity<ServiceResponseBean> fetchContractBlgdCodeAndPartyCodeAndWorkCodeByContractId(String contractId) ;
	
	ResponseEntity<ServiceResponseBean> fetchContractBlgdCodeAndCoyAndContractorByContractId(String contractId) ;
	
	ResponseEntity<ServiceResponseBean> fetchContractDebitByDebitTypeAndAuthnumAndRecIdAndCertAndRunserAndBldg(Map<String, Object> data);
	
	ResponseEntity<ServiceResponseBean> addContractDebit(List<ContractdebitRequestBean> contractdebitRequestBean);
	
	ResponseEntity<ServiceResponseBean> updateContractDebit(List<ContractdebitRequestBean> contractdebitRequestBean);
	
	ResponseEntity<ServiceResponseBean> reverseContratcDebit(List<ContractdebitRequestBean> contractdebitRequestBean);
	
	ResponseEntity<ServiceResponseBean> checkIsContractDebitAuthorised(String debitType, String authnum);
	
	ResponseEntity<ServiceResponseBean> fetchAuthBldgCodeAndCoy(String authnum);

	ResponseEntity<ServiceResponseBean> fetchContractBillDetailBySer(String ser);
	
	ResponseEntity<ServiceResponseBean> fetchContractDebitNoteBySer(String dbnoteser);
	
	ResponseEntity<ServiceResponseBean> fetchContractBillByRecIdAndBillNo(String recId, String billNo);
	
	ResponseEntity<ServiceResponseBean> fetchPartyByRecId(String recId);
	
	ResponseEntity<ServiceResponseBean> addCertificateDetail(CertificateRequestBean certificateRequestBean);

	ResponseEntity<ServiceResponseBean> checkIsDebitAmountValid(String recId, String billNo, Double debitAmount);

	ResponseEntity<ServiceResponseBean> addNewContractBillDetailBySer(ContractRequestBean contractRequestBean);
	
	ResponseEntity<ServiceResponseBean> addContractDebitNote(CdbnotehRequestBean cdbnotehRequestBean);
	
}