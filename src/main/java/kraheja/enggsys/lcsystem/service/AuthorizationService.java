package kraheja.enggsys.lcsystem.service;

import kraheja.enggsys.lcsystem.payload.db.SupplierDBResponse;
import kraheja.enggsys.lcsystem.payload.request.AuthorizationRequest;
import kraheja.payload.GenericResponse;

public interface AuthorizationService {
	SupplierDBResponse getSupplierDetail(String supplier, String building, String authType, String authnum);
	AuthorizationRequest retrieveAuthorization(String supplier, String building, String authType, String authnum);
	GenericResponse makeAuthorization(AuthorizationRequest request, String supplier, String building, String authType, String authnum);
}
