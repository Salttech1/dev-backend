package kraheja.enggsys.certificatesystem.reports.service.impl;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.enggsys.certificatesystem.reports.bean.request.ListOfInterimCertificateReportRequestBean;
import kraheja.enggsys.certificatesystem.reports.service.CertificateReportsService;

@Service
@Transactional
@Entity
public class CertificateReportsServiceImpl implements CertificateReportsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ResponseEntity<?> processListofInterimCertificateReport(
			ListOfInterimCertificateReportRequestBean listOfInterimCertificateReportRequestBean) {
		String sessionId, bldgCode, workCode, contractor, sqlWhere = "";
		Query query;
		try {
			bldgCode = listOfInterimCertificateReportRequestBean.getBldgCode();
			workCode = listOfInterimCertificateReportRequestBean.getWorkCode();
			contractor = listOfInterimCertificateReportRequestBean.getContractor();
			if (bldgCode != "") {
				sqlWhere = "cert_bldgcode = '" + bldgCode + "' AND ";
			}

			if (workCode != "") {
				sqlWhere = sqlWhere + "cert_workcode = '" + workCode + "' AND ";
			}
			if (contractor != "") {
				sqlWhere = sqlWhere + "cert_partycode = '" + contractor + "' AND ";
			}
			sqlWhere = sqlWhere.substring(0, sqlWhere.length() - 4);

			sessionId = GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS");

			query = this.entityManager.createNativeQuery("INSERT INTO temp_lp_lstofinterimpay ( "
					+ "cont_id 		,	bldg_code		,	bldg_name		,	work_code		,	work_name		, "
					+ "coy_code		,	coy_name		,	party_code		,	party_name		,	cert_certdate	,	sesid ) "
					+ "( SELECT cert_contract , cert_bldgcode , "
					+ "(SELECT bldg_name FROM building WHERE bldg_code = cert_bldgcode) AS BldgName , "
					+ "cert_workcode  , 	(SELECT ep_workname FROM epworks WHERE ep_workcode = cert_workcode "
					+ "AND (ep_closedate is null OR ep_closedate IN (SELECT ent_date1 FROM entity WHERE ent_class = 'ENGG' AND ent_id = 'WCLDT'))) AS WorkName , "
					+ "cert_coy , (SELECT coy_name FROM company WHERE coy_code = cert_coy AND coy_closedate = '01-Jan-2050') AS COY_NAME , "
					+ "Trim(cert_partycode) AS PARTY_CODE , (SELECT par_partyname FROM party WHERE par_partycode = cert_partycode AND par_partytype = 'E' "
					+ "AND sysdate BETWEEN par_opendate AND par_closedate) AS PARTY_NAME , Max(cert_certdate) AS cert_certdate , "
					+ sessionId.toString() + " FROM cert "
					+ "WHERE cert_contract || cert_certnum IN (SELECT cert_contract || Max(cert_certnum) FROM cert "
					+ "WHERE cert_contract IN (Select CERT_CONTRACT from cert where " + sqlWhere
					+ " AND cert_certstatus >= '5' "
					+ "AND cert_certstatus NOT IN ('6','8')) AND cert_certstatus >= '5' AND cert_certstatus NOT IN ('6','8') GROUP BY cert_contract ) "
					+ "AND cert_certtype = 'I' AND cert_certstatus >= 5 AND cert_certstatus NOT IN ('6','8')"
					+ "GROUP BY cert_contract , cert_bldgcode , cert_workcode , cert_coy , cert_partycode UNION "
					+ "SELECT cert_contract , cert_bldgcode  , (SELECT bldg_name FROM building WHERE bldg_code = cert_bldgcode) AS BLDG_NAME , "
					+ "cert_workcode , (SELECT ep_workname FROM epworks WHERE ep_workcode = cert_workcode "
					+ "AND (ep_closedate is null OR ep_closedate IN (SELECT ent_date1 FROM entity WHERE ent_class = 'ENGG' AND ent_id = 'WCLDT'))) AS WORK_NAME , "
					+ "cert_coy , (SELECT coy_name FROM company WHERE coy_code = cert_coy AND coy_closedate = '01-Jan-2050') AS COY_NAME , "
					+ "Trim(cert_partycode) AS PARTY_CODE , (SELECT par_partyname FROM party WHERE par_partycode = cert_partycode AND par_partytype = 'E' "
					+ "AND sysdate BETWEEN par_opendate AND par_closedate) AS PARTY_NAME , Max(cert_certdate) AS cert_certdate , "
					+ sessionId.toString()
					+ " FROM cert WHERE cert_contract IN (SELECT cert_contract  FROM cert WHERE cert_contract || cert_certnum IN ("
					+ "SELECT cert_contract || Max(cert_certnum) FROM cert WHERE cert_contract IN "
					+ "(Select CERT_CONTRACT from cert where " + sqlWhere
					+ " AND cert_certstatus >= '5' AND cert_certstatus NOT IN ('6','8') "
					+ ")	AND cert_certstatus >= '5' AND cert_certstatus NOT IN ('6','8')"
					+ "GROUP BY cert_contract ) "
					+ "AND cert_certtype = 'F' AND cert_certstatus < '5' GROUP BY cert_contract , cert_certtype , cert_certstatus) "
					+ "AND cert_certtype = 'I' AND cert_certstatus >= 5 AND cert_certstatus NOT IN ('6','8')"
					+ "GROUP BY cert_contract , cert_bldgcode , cert_workcode , cert_coy , cert_partycode)   ");

			Integer rowCount = query.executeUpdate();

		} catch (Exception e) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("FAILED for " + "     " + e.getMessage()).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(sessionId)
				.message("Added successfully").build());
	}

}
