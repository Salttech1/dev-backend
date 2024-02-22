package kraheja.adminexp.billing.dataentry.intercompany.service;

import kraheja.payload.GenericResponse;

public interface InterCoyPostingToAccountService {
	GenericResponse postInterCoy(String groupInvoiceNum);
}
