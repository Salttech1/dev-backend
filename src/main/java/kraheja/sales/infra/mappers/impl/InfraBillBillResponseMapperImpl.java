package kraheja.sales.infra.mappers.impl;

import org.springframework.stereotype.Component;

import kraheja.sales.entity.Infrbill;
import kraheja.sales.infra.bean.response.BillResponse;
import kraheja.sales.infra.mappers.InfraBillBillResponseMapper;

@Component
public class InfraBillBillResponseMapperImpl implements InfraBillBillResponseMapper{

	@Override
	public BillResponse mapInfraBillBillResponse(Infrbill infrbill) {
		return BillResponse
				.builder()
				.billNumber(infrbill.getInfrbillCK().getInfrBillnum())
				.ownerId(infrbill.getInfrbillCK().getInfrOwnerId())
				.month(infrbill.getInfrbillCK().getInfrMonth())
				.billDate(infrbill.getInfrBilldate())
				
				.admin(infrbill.getInfrAdmincharges())
				
				.build();
	}

}
