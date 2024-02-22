package kraheja.purch.materialpayments.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import kraheja.purch.bean.request.CancelMaterialPaymentRequestBean;
import kraheja.purch.bean.request.MaterialDetailRequestBean;
import kraheja.purch.bean.request.MaterialPaymentPrintRequestBean;
import kraheja.purch.bean.request.MaterialPaymentViewRequestBean;
import kraheja.purch.bean.request.PassMaterialPaymentRequestBean;
import kraheja.purch.bean.request.PrintStatusUpdateDetailRequestBean;
import kraheja.purch.bean.request.RemarkDetailRequestBean;
import kraheja.purch.bean.response.TempMatAuthPrintDetailResponseBean;

public interface MaterialPaymentsService {
	
	ResponseEntity<?> fetchAuthorisationEnquiry(String supplierCode, String authNos) ;
	
	ResponseEntity<?> fetchMaterialPaymentStatusEnquiry(String buildingCodes, String materialCodes, String supplierCodes, String authNos, String authTypes, String authFromDate, String authToDate) ;

	ResponseEntity<?> addMaterial(MaterialDetailRequestBean materialDetailRequestBean);

	ResponseEntity<?> fetchItemByBldgCodeAndMatGrp(String bldgCode, String matGrp);
	
	ResponseEntity<?> fetchAuthorisationsForPartyAndBuildingAndMatGroup(String partyCode, String buildingCode, String matGroup);

	ResponseEntity<?> fetchAuthorisationDetailsByAuthNum(String authNum);
	
	ResponseEntity<?> fetchPassMaterialPaymentList();
	
	ResponseEntity<?> checkIsAuthorisationValidForCancelation(String authTranser, String authStatus);
	
	ResponseEntity<?> cancelMaterialPayment(List<CancelMaterialPaymentRequestBean> cancelMaterialPaymentRequestBeanList);

	ResponseEntity<?> passMaterialPayment(PassMaterialPaymentRequestBean materialPaymentRequestBean);

	ResponseEntity<?> fetchAdvancePaidDetail(String partyCode, String buildingCode, String matGroup, String advanceAdjust);

	ResponseEntity<?> fetchExcludeBuildingDetail();

	ResponseEntity<?> fetchMaterialPaymentView(MaterialPaymentViewRequestBean materialPaymentViewRequestBean);

	ResponseEntity<?> fetchRemarksFromSuppbillno(RemarkDetailRequestBean remarkDetailRequestBean);

	ResponseEntity<?> fetchBillDetail(RemarkDetailRequestBean remarkDetailRequestBean);

	ResponseEntity<?> fetchPaidBillDetail(RemarkDetailRequestBean remarkDetailRequestBean);

	ResponseEntity<?> saveMaterialPayment(MaterialDetailRequestBean materialDetailRequestBean);

	ResponseEntity<?> fetchDebitTypeByBuilding(String buildingCode);

	ResponseEntity<?> insertIntoMaterialPaymentTemp(MaterialPaymentPrintRequestBean materialPaymentPrintRequestBean);

	ResponseEntity<?> updatePrintStatus(PrintStatusUpdateDetailRequestBean printStatusUpdateDetailRequestBean);

	ResponseEntity<?> fetchCodeHelpDetail(String tableName, String matLevel);

	ResponseEntity<?> deleteTempTableFromSessionId(Integer sessionId);

	ResponseEntity<?> mergePdf(TempMatAuthPrintDetailResponseBean tempMatAuthPrintDetailResponseBean);
}
