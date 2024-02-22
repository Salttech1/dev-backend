package kraheja.sales.infra.service;

import kraheja.sales.bean.request.InchequeRequest;
import kraheja.sales.bean.response.InchequeResponse;

public interface AuxiliaryPersistanceService {
	InchequeResponse saveIncheqe(String bldgCode, String wing, String flatNumber, String chargeCode, String billType, InchequeRequest inchequeRequest);
}