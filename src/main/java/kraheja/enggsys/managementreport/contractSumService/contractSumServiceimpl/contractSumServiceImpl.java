package kraheja.enggsys.managementreport.contractSumService.contractSumServiceimpl;

import java.lang.invoke.MethodHandles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import kraheja.arch.projbldg.dataentry.entity.Building;
import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActrandxRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.ActranhxRepository;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.sales.repository.OutinfraRepository;
import kraheja.sales.bean.request.InfraDefaultersListRequestBean;
import kraheja.sales.infra.service.InfraService;
import kraheja.enggsys.bean.request.contractSumRequestBean;
import kraheja.enggsys.managementreport.contractSumService.contractSumService;

@Service
@Transactional
public class contractSumServiceImpl implements contractSumService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private EntityRepository entityRepository;

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ResponseEntity<?> addIntoContractSummaryTempTable(contractSumRequestBean contractSumRequestBean) {
		String intPrmSessionID = GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS");

		String bldgCode = contractSumRequestBean.getasOnDate()
				.concat(contractSumRequestBean.getasOnDate().substring(6, 4))
				.concat(contractSumRequestBean.getasOnDate().substring(3, 2))
				.concat(contractSumRequestBean.getasOnDate().substring(0, 2));

		if (Objects.isNull(contractSumRequestBean.getasOnDate())) {
			contractSumRequestBean.setasOnDate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		}
		Query addIntoTempTableQuery = this.entityManager.createNativeQuery("INSERT INTO temp_contractsummary ( "
				+ "    tcts_projcode, " + "    tcts_matcerttype, " + "    tcts_matcertcode, " + "    tcts_groupcode, "
				+ "    tcts_subgroupcode, " + "    tcts_partytype, " + "    tcts_partycode, " + "    tcts_estimateamt, "
				+ "    tcts_contractamt, " + "    tcts_site, " + "    tcts_userid, " + "    tcts_today, "
				+ "    tcts_sessionid " + ") " + "SELECT "
				+ "    '\" + TxtBldgCode.Text.Trim() + \"' AS tcts_projcode, " + "    type AS tcts_matcerttype, "
				+ "    matcertcode AS tcts_matcertcode, " + "    groupcode AS tcts_groupcode, "
				+ "    subgroupcode AS tcts_subgroupcode, " + "    partytype AS tcts_partytype, "
				+ "    partycode AS tcts_partycode, " + "    estimateamt AS tcts_estimateamt, "
				+ "    (NVL(totalamount, 0) + NVL(advance, 0) - NVL(advadjusted, 0) - NVL(retention, 0) + NVL(relofret, 0)) AS tcts_contractamt, "
				+ "    '\" + ClsGlobalSettings.ClsGlobalSettings.StrPubSite + \"' AS tcts_site, "
				+ "    '\" + ClsGlobalSettings.ClsGlobalSettings.StrPubUserName + \"' AS tcts_userid, "
				+ "    SYSDATE AS tcts_today, " + "    IntPriSessionID.toString() AS tcts_sessionid " + "FROM ( "
				+ "    SELECT 'Labour' AS type, " + "        ( " + "            SELECT cls_groupcode "
				+ "            FROM matcertclassification "
				+ "            WHERE cls_matcerttype = 'Labour' AND cls_matcertcode = cert_workcode "
				+ "        ) AS groupcode, " + "        ( " + "            SELECT cls_subgroupcode "
				+ "            FROM matcertclassification "
				+ "            WHERE cls_matcerttype = 'Labour' AND cls_matcertcode = cert_workcode "
				+ "        ) AS subgroupcode, " + "        c.cert_workcode AS matcertcode, " + "        ( "
				+ "            SELECT ep_workname " + "            FROM epworks "
				+ "            WHERE ep_workcode = c.cert_workcode AND ep_closedate IS NULL "
				+ "        ) AS matcertname, " + "        'E' AS partytype, "
				+ "        c.cert_partycode AS partycode, " + "        SUM( " + "            CASE c.cert_certtype "
				+ "                WHEN 'A' THEN 0 " + "                WHEN 'L' THEN 0 "
				+ "                ELSE c.cert_certamount " + "            END " + "        ) AS cert_certamount, "
				+ "        SUM( " + "            CASE c.cert_certtype "
				+ "                WHEN 'A' THEN c.cert_certamount " + "                ELSE 0 " + "            END "
				+ "        ) AS advance, " + "        SUM( " + "            CASE c.cert_certtype "
				+ "                WHEN 'A' THEN 0 " + "                ELSE c.cert_advadjusted " + "            END "
				+ "        ) AS advadjusted, " + "        SUM( " + "            CASE c.cert_certtype "
				+ "                WHEN 'A' THEN 0 " + "                ELSE c.cert_retained " + "            END "
				+ "        ) AS retention, " + "        SUM( " + "            CASE c.cert_certtype "
				+ "                WHEN 'L' THEN c.cert_certamount " + "                ELSE 0 " + "            END "
				+ "        ) AS relretamt " + "    FROM cert c "
				+ "    WHERE c.cert_bldgcode = '\" + TxtBldgCode.Text.Trim() + \"' "
				+ "        AND c.cert_certstatus IN ('5', '7') "
				+ "        AND TRUNC(c.cert_passedon) <= '\" + StrLocasOnDate + \"' "
				+ "    GROUP BY c.cert_workcode, c.cert_partycode " + " " + "    UNION ALL " + " "
				+ "    SELECT 'Material' AS type, " + "        ( " + "            SELECT cls_groupcode "
				+ "            FROM matcertclassification "
				+ "            WHERE cls_matcerttype = 'Material' AND cls_matcertcode = auth_matgroup "
				+ "        ) AS groupcode, " + "        ( " + "            SELECT cls_subgroupcode "
				+ "            FROM matcertclassification "
				+ "            WHERE cls_matcerttype = 'Material' AND cls_matcertcode = auth_matgroup "
				+ "        ) AS subgroupcode, " + "        c.auth_matgroup AS matcertcode, " + "        ( "
				+ "            SELECT mat_matname " + "            FROM material "
				+ "            WHERE mat_level = '1' AND mat_matgroup = auth_matgroup " + "        ) AS matcertname, "
				+ "        'S' AS partytype, " + "        c.auth_partycode AS partycode, " + "        SUM( "
				+ "            CASE c.auth_authtype " + "                WHEN 'A' THEN 0 "
				+ "                WHEN 'L' THEN 0 " + "                ELSE c.auth_authamount " + "            END "
				+ "        ) AS auth_authamount, " + "        SUM( " + "            CASE c.auth_authtype "
				+ "                WHEN 'A' THEN c.auth_authamount " + "                ELSE 0 " + "            END "
				+ "        ) AS advance, " + "        SUM( " + "            CASE c.auth_authtype "
				+ "                WHEN 'A' THEN 0 " + "                ELSE c.auth_advadjust " + "            END "
				+ "        ) AS advadjusted, " + "        SUM(c.auth_retained) AS retention, " + "        SUM( "
				+ "            CASE c.auth_authtype " + "                WHEN 'L' THEN c.auth_authamount "
				+ "                ELSE 0 " + "            END " + "        ) AS relretamt " + "    FROM auth_h c "
				+ "    WHERE c.auth_bldgcode = '\" + TxtBldgCode.Text.Trim() + \"' "
				+ "        AND c.auth_authstatus IN ('5', '7') "
				+ "        AND TRUNC(c.auth_passedon) <= '\" + StrLocasOnDate + \"' "
				+ "    GROUP BY c.auth_matgroup, c.auth_partycode )" + " GROUP BY groupcode," + "subgroupcode,"
				+ " type," + " matcertcode," + " partytype," + " partycode");
		addIntoTempTableQuery.setParameter("intPrmSessionID", intPrmSessionID);
		addIntoTempTableQuery.setParameter("StrPrmBldgCode", bldgCode);
		// addIntoTempTableQuery.setParameter("StrPrmBldgCode",
		// contractsumRequestBean.getBldgCode());
		addIntoTempTableQuery.setParameter("DatPrmasOnDate", contractSumRequestBean.getasOnDate());
//		addIntoTempTableQuery.setParameter("userId", GenericAuditContextHolder.getContext().getUserid());
//		addIntoTempTableQuery.setParameter("userId", "YVICKY");
//		addIntoTempTableQuery.setParameter("today", LocalDateTime.now());

		LOGGER.info("QUERY :: {}", addIntoTempTableQuery);
		Integer rowCount = addIntoTempTableQuery.executeUpdate();
		if (rowCount == 0) {
			this.entityRepository.updateIncrementCounter("#SESS", "#SESS", Double.valueOf(intPrmSessionID));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Query addIntooTempTableQuery = this.entityManager.createNativeQuery("INSERT INTO TEMP_CONTRACTSUMMARY (\r\n"
				+ "    TCTS_PROJCODE,\r\n" + "    TCTS_MATCERTTYPE,\r\n" + "    TCTS_MATCERTCODE,\r\n"
				+ "    TCTS_GROUPCODE,\r\n" + "    TCTS_SUBGROUPCODE,\r\n" + "    TCTS_PARTYTYPE,\r\n"
				+ "    TCTS_PARTYCODE,\r\n" + "    TCTS_ESTIMATEAMT,\r\n" + "    TCTS_CONTRACTAMT,\r\n"
				+ "    TCTS_TENDERDATE,\r\n" + "    TCTS_QUOTEDATE,\r\n" + "    TCTS_ORDERDATE,\r\n"
				+ "    TCTS_DELIVERYDATE,\r\n" + "    TCTS_DELIVERYWEEKS,\r\n" + "    TCTS_ACTIONBY,\r\n"
				+ "    TCTS_SITE,\r\n" + "    TCTS_USERID,\r\n" + "    TCTS_TODAY,\r\n" + "    TCTS_SESSIONID\r\n"
				+ ")\r\n" + "SELECT\r\n" + "    CLSE_PROJCODE,\r\n" + "    CLSE_MATCERTTYPE,\r\n"
				+ "    CLSE_MATCERTCODE,\r\n" + "    CLSE_GROUPCODE,\r\n" + "    CLSE_SUBGROUPCODE,\r\n"
				+ "    CLSE_PARTYTYPE,\r\n" + "    CLSE_PARTYCODE,\r\n" + "    CLSE_ESTIMATEAMT,\r\n"
				+ "    CLSE_CONTRACTVAL\r\n" + "    CLSE_TENDERDATE\r\n" + "    CLSE_QUOTEDATE,\r\n"
				+ "    CLSE_ORDERDATE,\r\n" + "    CLSE_DELIVERYDATE,\r\n" + "    CLSE_DELIVERYWEEKS,\r\n"
				+ "    CLSE_ACTIONBY,\r\n"
				+ "    '\" + ClsGlobalSettings.ClsGlobalSettings.StrPubSite + \"' AS TCTS_SITE,\r\n"
				+ "    '\" + ClsGlobalSettings.ClsGlobalSettings.StrPubUserName + \"' AS TCTS_USERID,\r\n"
				+ "    SYSDATE AS TCTS_TODAY,\r\n" + "    IntPriSessionID.toString AS TCTS_SESSIONID\r\n"
				+ "FROM MATCERTESTIMATEACTUAL A\r\n" + "WHERE A.CLSE_PROJCODE = '\" + TxtBldgCode.Text.Trim + \"'\r\n"
				+ "AND (\r\n"
				+ "    A.CLSE_PROJCODE || A.CLSE_MATCERTTYPE || A.CLSE_MATCERTCODE || A.CLSE_GROUPCODE || A.CLSE_SUBGROUPCODE || A.CLSE_PARTYTYPE || A.CLSE_PARTYCODE \r\n"
				+ ") NOT IN (\r\n" + "    SELECT \"\r\n"
				+ "        B.TCTS_PROJCODE || B.TCTS_MATCERTTYPE || B.TCTS_MATCERTCODE || B.TCTS_GROUPCODE || B.TCTS_SUBGROUPCODE || B.TCTS_PARTYTYPE || B.TCTS_PARTYCODE \r\n"
				+ "   FROM TEMP_CONTRACTSUMMARY B \"\r\n"
				+ " \"    WHERE B.TCTS_SESSIONID = \" + IntPriSessionID.ToString + \"\r\n");
		addIntooTempTableQuery.setParameter("intPrmSessionID", intPrmSessionID);
		addIntoTempTableQuery.setParameter("StrPrmBldgCode", bldgCode);
		// addIntooTempTableQuery.setParameter("StrPrmBldgCode",
		// contractsumRequestBean.getBldgCode());
		addIntooTempTableQuery.setParameter("DatPrmasOnDate", contractSumRequestBean.getasOnDate());
//		addIntoTempTableQuery.setParameter("userId", GenericAuditContextHolder.getContext().getUserid());
//		addIntoTempTableQuery.setParameter("userId", "YVICKY");
//		addIntoTempTableQuery.setParameter("today", LocalDateTime.now());

		LOGGER.info("QUERY :: {}", addIntooTempTableQuery);
		Integer rowCount1 = addIntooTempTableQuery.executeUpdate();

		if (rowCount1 == 0) {
			this.entityRepository.updateIncrementCounter("#SESS", "#SESS", Double.valueOf(intPrmSessionID));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Query udateIntoTempTableQuery = this.entityManager.createNativeQuery("UPDATE TEMP_CONTRACTSUMMARY B \r\n"
				+ "SET \r\n" + "    B.TCTS_TENDERDATE = (\r\n" + "        SELECT A.CLSE_TENDERDATE \r\n"
				+ "        FROM MATCERTESTIMATEACTUAL A \r\n"
				+ "        WHERE B.TCTS_PROJCODE || B.TCTS_MATCERTTYPE || B.TCTS_MATCERTCODE || B.TCTS_GROUPCODE || B.TCTS_SUBGROUPCODE || B.TCTS_PARTYTYPE || B.TCTS_PARTYCODE =\r\n"
				+ "            A.CLSE_PROJCODE || A.CLSE_MATCERTTYPE || A.CLSE_MATCERTCODE || A.CLSE_GROUPCODE || A.CLSE_SUBGROUPCODE || A.CLSE_PARTYTYPE || A.CLSE_PARTYCODE \r\n"
				+ "    ),\r\n" + "    B.TCTS_QUOTEDATE = (\r\n" + "        SELECT A.CLSE_QUOTEDATE \r\n"
				+ "       FROM MATCERTESTIMATEACTUAL A \r\n"
				+ "        WHERE B.TCTS_PROJCODE || B.TCTS_MATCERTTYPE || B.TCTS_MATCERTCODE || B.TCTS_GROUPCODE || B.TCTS_SUBGROUPCODE || B.TCTS_PARTYTYPE || B.TCTS_PARTYCODE = \r\n"
				+ "            A.CLSE_PROJCODE || A.CLSE_MATCERTTYPE || A.CLSE_MATCERTCODE || A.CLSE_GROUPCODE || A.CLSE_SUBGROUPCODE || A.CLSE_PARTYTYPE || A.CLSE_PARTYCODE \r\n"
				+ "    ),\r\n" + "    B.TCTS_ORDERDATE = ( \r\n" + "        SELECT A.CLSE_ORDERDATE \r\n"
				+ "        FROM MATCERTESTIMATEACTUAL A \r\n"
				+ "        WHERE B.TCTS_PROJCODE || B.TCTS_MATCERTTYPE || B.TCTS_MATCERTCODE || B.TCTS_GROUPCODE || B.TCTS_SUBGROUPCODE || B.TCTS_PARTYTYPE || B.TCTS_PARTYCODE = \r\n"
				+ "            A.CLSE_PROJCODE || A.CLSE_MATCERTTYPE || A.CLSE_MATCERTCODE || A.CLSE_GROUPCODE || A.CLSE_SUBGROUPCODE || A.CLSE_PARTYTYPE || A.CLSE_PARTYCODE \r\n"
				+ "    ), \r\n" + "    B.TCTS_DELIVERYDATE = ( \r\n" + "        SELECT A.CLSE_DELIVERYDATE \r\n"
				+ "       FROM MATCERTESTIMATEACTUAL A \r\n"
				+ "        WHERE B.TCTS_PROJCODE || B.TCTS_MATCERTTYPE || B.TCTS_MATCERTCODE || B.TCTS_GROUPCODE || B.TCTS_SUBGROUPCODE || B.TCTS_PARTYTYPE || B.TCTS_PARTYCODE = \r\n"
				+ "            A.CLSE_PROJCODE || A.CLSE_MATCERTTYPE || A.CLSE_MATCERTCODE || A.CLSE_GROUPCODE || A.CLSE_SUBGROUPCODE || A.CLSE_PARTYTYPE || A.CLSE_PARTYCODE \r\n"
				+ "    ), \r\n" + "    B.TCTS_DELIVERYWEEKS = ( \r\n" + "        SELECT A.CLSE_DELIVERYWEEKS \"\r\n"
				+ "        FROM MATCERTESTIMATEACTUAL A \"\r\n"
				+ "        WHERE B.TCTS_PROJCODE || B.TCTS_MATCERTTYPE || B.TCTS_MATCERTCODE || B.TCTS_GROUPCODE || B.TCTS_SUBGROUPCODE || B.TCTS_PARTYTYPE || B.TCTS_PARTYCODE = \r\n"
				+ "            A.CLSE_PROJCODE || A.CLSE_MATCERTTYPE || A.CLSE_MATCERTCODE || A.CLSE_GROUPCODE || A.CLSE_SUBGROUPCODE || A.CLSE_PARTYTYPE || A.CLSE_PARTYCODE \r\n"
				+ "    ), \r\n" + "    B.TCTS_ACTIONBY = ( \r\n" + "        SELECT A.CLSE_ACTIONBY \r\n"
				+ "        FROM MATCERTESTIMATEACTUAL A \r\n"
				+ "        WHERE B.TCTS_PROJCODE || B.TCTS_MATCERTTYPE || B.TCTS_MATCERTCODE || B.TCTS_GROUPCODE || B.TCTS_SUBGROUPCODE || B.TCTS_PARTYTYPE || B.TCTS_PARTYCODE = \r\n"
				+ "            A.CLSE_PROJCODE || A.CLSE_MATCERTTYPE || A.CLSE_MATCERTCODE || A.CLSE_GROUPCODE || A.CLSE_SUBGROUPCODE || A.CLSE_PARTYTYPE || A.CLSE_PARTYCODE \r\n"
				+ "    ) \r\n" + "WHERE \r\n"
				+ "    B.TCTS_PROJCODE || B.TCTS_MATCERTTYPE || B.TCTS_MATCERTCODE || B.TCTS_GROUPCODE || B.TCTS_SUBGROUPCODE || B.TCTS_PARTYTYPE || B.TCTS_PARTYCODE IN ( \r\n"
				+ "        SELECT A.CLSE_PROJCODE || A.CLSE_MATCERTTYPE || A.CLSE_MATCERTCODE || A.CLSE_GROUPCODE || A.CLSE_SUBGROUPCODE || A.CLSE_PARTYTYPE || A.CLSE_PARTYCODE \r\n"
				+ "        FROM MATCERTESTIMATEACTUAL A \r\n" + "    ) \r\n" + "   AND B.TCTS_SESSIONID = "
				+ "sessionId.toString()");
		udateIntoTempTableQuery.setParameter("intPrmSessionID", intPrmSessionID);
		addIntoTempTableQuery.setParameter("StrPrmBldgCode", bldgCode);
		// udateIntoTempTableQuery.setParameter("StrPrmBldgCode",
		// contractsumRequestBean.getBldgCode());
		udateIntoTempTableQuery.setParameter("DatPrmasOnDate", contractSumRequestBean.getasOnDate());
//		addIntoTempTableQuery.setParameter("userId", GenericAuditContextHolder.getContext().getUserid());
//		addIntoTempTableQuery.setParameter("userId", "YVICKY");
//		addIntoTempTableQuery.setParameter("today", LocalDateTime.now());

		LOGGER.info("QUERY :: {}", udateIntoTempTableQuery);
		Integer rowCount2 = udateIntoTempTableQuery.executeUpdate();

		if (rowCount2 == 0) {
			this.entityRepository.updateIncrementCounter("#SESS", "#SESS", Double.valueOf(intPrmSessionID));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(intPrmSessionID)
				.message("Added successfully").build());
	}

	@Override
	public ResponseEntity<?> fetchMatcertGroup(String bldgCode) {
		LOGGER.info("bldgCode: {} ", bldgCode);
		String Labour = "";
		String Material = "";

		Query query;
		query = this.entityManager.createNativeQuery("SELECT \r\n" + "   'Labour' AS cls_matcerttype, \r\n"
				+ "    cert_workcode AS cls_matcertcode, \r\n" + "   (\r\n" + "    SELECT ep_workname \r\n"
				+ "        FROM epworks \r\n" + "       WHERE ep_workcode = cert_workcode \r\n"
				+ "        AND ep_closedate IS NULL \r\n" + "    ) AS workname, \r\n" + "    '' AS cls_groupcode, \r\n"
				+ "   '' AS cls_site, \r\n" + "   '' AS cls_userid, \r\n" + "    SYSDATE AS cls_today,\r\n"
				+ "    '' AS cls_origsite \r\n" + "FROM cert \r\n"
				+ "WHERE cert_bldgcode = '\" + StrLocBldgCode + \"'\r\n" + "AND cert_workcode NOT IN ( \r\n"
				+ "    SELECT cls_matcertcode \r\n" + "    FROM matcertclassification \r\n"
				+ "    WHERE cls_matcerttype = 'Labour' \r\n" + "    ) \r\n" + "    UNION ALL \r\n" + "SELECT \r\n"
				+ "    'Material' AS cls_matcerttype, \r\n" + "    auth_matgroup AS cls_matcertcode, \r\n"
				+ "    ( \r\n" + "        SELECT mat_matname \r\n" + "        FROM MATERIAL \r\n"
				+ "        WHERE mat_matgroup = auth_matgroup \r\n" + "        AND mat_matcode = '****' \r\n"
				+ "        AND mat_itemcode = '********' \r\n" + "    ) AS workname, \r\n"
				+ "   '' AS cls_groupcode, \r\n" + "   '' AS cls_site, \r\n" + "   '' AS cls_userid, \r\n"
				+ "  SYSDATE AS cls_today, \r\n" + "    '' AS cls_origsite \r\n" + "FROM auth_h \"\r\n"
				+ "WHERE auth_bldgcode = '\" + StrLocBldgCode + '\r\n" + "\"AND auth_matgroup NOT IN ( \r\n"
				+ "\"    SELECT cls_matcertcode \r\n" + "\"    FROM matcertclassification \r\n"
				+ "\"    WHERE cls_matcerttype = 'Material' \r\n");

		Labour = String.valueOf(query.getSingleResult());
		Material = String.valueOf(query.getSingleResult());
		LOGGER.info("Labour: {} ", Labour);
		if (Objects.nonNull(Labour) && !Labour.isEmpty()) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(Labour).build());

		}
		LOGGER.info("Material: {} ", Material);
		if (Objects.nonNull(Material) && !Material.isEmpty()) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(Material).build());

		}
		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Labour & Material Not Found").build());

	}

	@Override
	public ResponseEntity<?> deleteContractSummaryFromSessionId(Integer intPrmSessionID) {
		Query deleteBySessIdTempTableQuery = this.entityManager
				.createNativeQuery("delete from sainrp05 where sum_sesid = :sessionId");
		deleteBySessIdTempTableQuery.setParameter("intPrmSessionID", intPrmSessionID);
		deleteBySessIdTempTableQuery.executeUpdate();

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}
}
