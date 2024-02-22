package kraheja.adminexp.billing.dataentry.intercompany.service;

import kraheja.adminexp.billing.dataentry.intercompany.bean.request.InterCompanyRequest;
import kraheja.adminexp.billing.dataentry.intercompany.bean.response.AddInterCompanyResponse;
import kraheja.payload.GenericResponse;

public interface InterCompanyService {
	AddInterCompanyResponse fetchInterCompanyDetails(InterCompanyRequest request);

	AddInterCompanyResponse retriveInterCompanyDetails(InterCompanyRequest request);

	GenericResponse persistInterCompany(AddInterCompanyResponse request);
	
}
