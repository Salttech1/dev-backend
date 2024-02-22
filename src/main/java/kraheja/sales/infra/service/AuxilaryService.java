package kraheja.sales.infra.service;

import kraheja.sales.bean.request.AuxilaryRequest;
import kraheja.sales.bean.response.AuxilaryResponse;

public interface AuxilaryService {
	AuxilaryResponse getGridData(AuxilaryRequest auxilaryRequest);
}