package kraheja.commons.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.response.DynaPopResponseBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Dynapop;
import kraheja.commons.repository.DynaPopRepository;
import kraheja.commons.service.DynaPopService;
import kraheja.commons.utils.CommonUtils;

@Service
public class DynaPopServiceImpl implements DynaPopService {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private DynaPopRepository dynaPopRepository;

	@Autowired
	private EntityManager entityManager;

	public ResponseEntity<?> dynaPop(String dynaPopId, String queryConditon) {
		Dynapop dynapopEntity = this.dynaPopRepository.findByDynapopId(dynaPopId.trim());
		logger.info("DynapopEntity :: {}", dynapopEntity);

		if(dynapopEntity != null) {
			Query q = entityManager.createNativeQuery(CommonUtils.INSTANCE.dynaPop(dynapopEntity, queryConditon).toString());
			List<Object[]> resultSet = q.getResultList();
			logger.info("ResultSet :: {}", resultSet);

			return ResponseEntity.ok(ServiceResponseBean.builder()
					.data(DynaPopResponseBean.builder()
							.dataSet(resultSet)
							.bringBackColumn(dynapopEntity.getDynBbc())
							.colhead1(dynapopEntity.getDynColhead1() != null ? dynapopEntity.getDynColhead1() : dynapopEntity.getDynColumn1())
							.colhead2(dynapopEntity.getDynColhead2() != null ? dynapopEntity.getDynColhead2() : dynapopEntity.getDynColumn2())
							.colhead3(dynapopEntity.getDynColhead3() != null ? dynapopEntity.getDynColhead3() : dynapopEntity.getDynColumn3())
							.colhead4(dynapopEntity.getDynColhead4() != null ? dynapopEntity.getDynColhead4() : dynapopEntity.getDynColumn4())
							.colhead5(dynapopEntity.getDynColhead5() != null ? dynapopEntity.getDynColhead5() : dynapopEntity.getDynColumn5())
							.mainheader(dynapopEntity.getDynHeader())
							.build())
					.status(Boolean.TRUE)
					.build());
		}
		return ResponseEntity.ok("No Data Found");
	}

	@Override
	public ResponseEntity<?> dynaPopFindBySearchText(String dynaPopId, String queryConditon, String searchText) {
		Dynapop dynapopEntity = this.dynaPopRepository.findByDynapopId(dynaPopId.trim());
		logger.info("DynapopEntity :: {}", dynapopEntity);

		if(dynapopEntity != null) {
			if (searchText.contains("'"))
				searchText = searchText.replaceAll("'", "`");	
			
			Query q = entityManager.createNativeQuery(CommonUtils.INSTANCE.dynaPopFindBy(dynapopEntity, queryConditon, searchText.toUpperCase()).toString());
			List<Object[]> resultSet = q.getResultList();
			logger.info("ResultSet :: {}", resultSet);
			if(resultSet != null)
			return ResponseEntity.ok(ServiceResponseBean.builder()
					.data(DynaPopResponseBean.builder()
							.dataSet(resultSet)
							.bringBackColumn(dynapopEntity.getDynBbc())
							.colhead1(dynapopEntity.getDynColhead1() != null ? dynapopEntity.getDynColhead1() : dynapopEntity.getDynColumn1())
							.colhead2(dynapopEntity.getDynColhead2() != null ? dynapopEntity.getDynColhead2() : dynapopEntity.getDynColumn2())
							.colhead3(dynapopEntity.getDynColhead3() != null ? dynapopEntity.getDynColhead3() : dynapopEntity.getDynColumn3())
							.colhead4(dynapopEntity.getDynColhead4() != null ? dynapopEntity.getDynColhead4() : dynapopEntity.getDynColumn4())
							.colhead5(dynapopEntity.getDynColhead5() != null ? dynapopEntity.getDynColhead5() : dynapopEntity.getDynColumn5())
							.mainheader(dynapopEntity.getDynHeader())
							.build())
					.status(Boolean.TRUE)
					.build());
		}
		return ResponseEntity.ok("No Data Found");
	}
}