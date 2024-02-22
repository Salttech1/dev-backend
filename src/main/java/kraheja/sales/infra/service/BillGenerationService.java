package kraheja.sales.infra.service;

import kraheja.sales.bean.response.GetNextBillDateResponse;
import kraheja.sales.infra.bean.request.InfraAuxiBillRequest;
import kraheja.sales.infra.bean.response.BillResponse;

public interface BillGenerationService {

	BillResponse getBillDetail(InfraAuxiBillRequest billRequest);
	GetNextBillDateResponse getNextBillDate(InfraAuxiBillRequest billRequest);
}
