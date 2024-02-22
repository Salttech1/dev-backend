package kraheja.commons.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.service.GenericResultService;

@Service
@Transactional
public class GenericResultServiceImpl implements GenericResultService {

	@PersistenceContext
	
	private EntityManager entityManager;

	@Override
	public ResponseEntity<?> fetchResultByTableColumn(String fetchcolumn, String tableName, String columnName,
			String columnValue) {
		// TODO Auto-generated method stub
		 Query createNativeQuery = entityManager.createNativeQuery("select " + fetchcolumn + " from " + tableName 
				+ " where "+ columnName + "='" + columnValue + "'");
		 return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(createNativeQuery.getSingleResult().toString()).build());
		 
	}}