package kraheja.sales.infra.mappers;

import kraheja.sales.entity.Infrbill;
import kraheja.sales.infra.bean.response.BillResponse;

public interface InfraBillBillResponseMapper {

	BillResponse mapInfraBillBillResponse(Infrbill infrbill);
}
