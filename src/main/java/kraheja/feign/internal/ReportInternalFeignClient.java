package kraheja.feign.internal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import kraheja.commons.bean.request.ActiveDepositReportRequestBean;
import kraheja.commons.bean.request.OSAdvAndRetReportRequestBean;
import kraheja.commons.bean.request.ReportMasterRequestBean;

//@ConditionalOnProperty(value = "feign.client.kraheja-service-name")
@FeignClient(value = "${feign.client.crystal-report-service-name}", url = "${feign.client.url}")
public interface ReportInternalFeignClient {

	 @PostMapping(value = "/report/generate-report-ttx")
	 public byte[] generateReportTtx(@RequestBody ReportMasterRequestBean reportMasterRequestBean );
	 
	 @PostMapping(value = "/report/generate-report-parameter")
	 public byte[] generateReportWithParameter(@RequestBody ReportMasterRequestBean reportMasterRequestBean );
	 
	 @PostMapping(value = "/report/generate-ttx-report-with-only-formula")
	 public byte[] generateTtxReportWithOnlyFormula(@RequestBody ReportMasterRequestBean reportMasterRequestBean );
	 
	 @PostMapping(value = "/report/generate-report-ttx-condition")
	 public byte[] generateReportTtxWithCondition(@RequestBody ReportMasterRequestBean reportMasterRequestBean );
	 
	 @PostMapping(value = "/report/generate-report-condition-and-parameter")
	 public byte[] generateReportWithConditionAndParameter(@RequestBody ReportMasterRequestBean reportMasterRequestBean );
	 
	 @PostMapping(value = "/report/generate-report-with-multiple-condition-and-parameter")
	 public byte[] generateReportWithMultipleConditionAndParameter(@RequestBody ReportMasterRequestBean reportMasterRequestBean );

	 @PostMapping(value = "/custom-report/generate-active-deposit-report")
	 public byte[] generateActiveDepositReport(@RequestBody ActiveDepositReportRequestBean activeDepositReportRequestBean);
	 
	 @PostMapping(value = "/custom-report/generate-outstanding-adv-and-reten-report")
	 public byte[] generateOutStandingAdvanceAndRetentionReport(@RequestBody OSAdvAndRetReportRequestBean OSAdvAndRetReportRequestBean);
}	