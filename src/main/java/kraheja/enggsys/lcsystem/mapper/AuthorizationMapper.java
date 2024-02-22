package kraheja.enggsys.lcsystem.mapper;

import java.time.LocalDate;
import java.util.List;

import kraheja.enggsys.entity.Lcauth;
import kraheja.enggsys.entity.Lcauthboe;
import kraheja.enggsys.lcsystem.payload.db.SupplierDBResponse;
import kraheja.enggsys.lcsystem.payload.request.AuthorizationRequest;
import kraheja.enggsys.lcsystem.payload.request.LcDetails;


public interface AuthorizationMapper {
	public AuthorizationRequest lcauthRequestMap(Lcauth lcauth, List<Lcauthboe> lcauthboe);
	public Lcauth requestLcauthMap(AuthorizationRequest request);
	public List<Lcauthboe> lcDetailToLcauthboe(List<LcDetails> lcDetailsList, String authNum, SupplierDBResponse supplier, String bldgCode, String docNo, LocalDate docDate );
}
