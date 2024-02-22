package kraheja.commons.service.impl;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.service.CommonMethodsService;

@Service
@Transactional
@Entity
public class CommonMethodsServiceImpl implements CommonMethodsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ResponseEntity<ServiceResponseBean> deleteRowsFromTempTable(String tempTableName, String sesIdColName,
			String sessionId) {
		Query deleteBySessIdTempTableQuery = this.entityManager.createNativeQuery(
				"Delete From " + tempTableName + " where " + sesIdColName + " = '" + sessionId + "' ");
		deleteBySessIdTempTableQuery.executeUpdate();
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}

}
