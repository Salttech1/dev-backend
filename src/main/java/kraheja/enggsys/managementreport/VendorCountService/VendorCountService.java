package kraheja.enggsys.managementreport.VendorCountService;

import kraheja.enggsys.managementreport.bean.response.GenericResponse;


public interface VendorCountService {

	GenericResponse<String> fetchVendorCountByLogicNoteNum(String logicNoteNum);
}